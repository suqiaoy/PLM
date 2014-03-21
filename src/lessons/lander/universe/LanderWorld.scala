package lessons.lander.universe

import javax.script.ScriptEngine
import javax.swing.ImageIcon

import plm.universe.World
import plm.core.model.ProgrammingLanguage
import plm.core.ui.ResourcesCache

import Math.PI
import Math.cos
import Math.sin

object NumUtil {
  def clamp(min: Double, max: Double, value: Double): Double = {
    if (value < min) min else if (value > max) max else value
  }
  def clamp(min: Int, max: Int, value: Int): Int = {
    if (value < min) min else if (value > max) max else value
  }

  def angleToVector(angle: Double) = Point(cos(angle), sin(angle))
}

case class Point(x: Double, y: Double) {
  def +(p: Point): Point = new Point(x + p.x, y + p.y)
  def -(p: Point): Point = new Point(x - p.x, y - p.y)
  def *(l: Double): Point = new Point(x * l, y * l)
  def /(l: Double): Point = new Point(x / l, y / l)
  def unary_- = this * -1

  def length: Double = Math.sqrt(x * x + y * y)
  def normed = this / length
  def dot(p: Point) = x * p.x + y * p.y
  def cross(p: Point) = x * p.y - y * p.x
}

case class Segment(start: Point, end: Point)

case class Ray(origin: Point, direction: Point) {
  def intersects(s: Segment): Boolean = {
    val v = s.end - s.start
    val cross = direction.cross(v);
    if (cross == 0) {
      false
    } else {
      val f1 = (s.start - origin).cross(v) / cross
      val f2 = (s.start - origin).cross(direction) / cross
      f1 >= 0 && f2 >= 0 && f2 <= 1
    }
  }
}

object LanderWorld {
  object State extends Enumeration {
    val FLYING, LANDED, CRASHED, OUT = Value
  }

  private val GRAVITY = Point(0, -1) * 3.711;
}

class LanderWorld(val parent: DelegatingLanderWorld) {

  import LanderWorld.State._
  import NumUtil._

  var width: Int = 0
  var height: Int = 0
  var ground: List[Point] = null
  var position: Point = null
  var speed: Point = null
  /** Angle in degrees, 0 points north, 90 points west. */
  var angle: Double = 0
  var thrust: Int = 0
  var state = FLYING

  var desiredAngle: Double = 0
  var desiredThrust: Int = 0

  parent.setDelay(10)
  parent.addEntity(new LanderEntity())

  // "inherited" methods

  def getIcon = ResourcesCache.getIcon("img/world_lander.png");

  def setupBindings(lang: ProgrammingLanguage, engine: ScriptEngine): Unit = ()

  /** Returns true if both worlds have same state. */
  override def equals(obj: Any): Boolean = {
    obj match {
      case world: DelegatingLanderWorld => state == world.realWorld.state
      case _ => false
    }
  }

  def diffTo(world: World): String = null

  def reset(initialWorld: LanderWorld): Unit = {
    width = initialWorld.width
    height = initialWorld.height
    ground = initialWorld.ground
    position = initialWorld.position
    speed = initialWorld.speed
    angle = initialWorld.angle
    thrust = initialWorld.thrust
    state = initialWorld.state
    desiredAngle = angle
    desiredThrust = thrust
  }

  def getView() = new LanderWorldView(LanderWorld.this)

  override def toString() = "scala lander world"

  // simulation
    
  def angleRadian = (angle + 90) * PI / 180

  private def groundSegments = {
    if (ground.isEmpty) List()
    else (ground, ground.tail).zipped map (Segment(_, _))
  }

  private def flatSegments = groundSegments.filter((s) => s.start.y == s.end.y)

  private def touchesSomeFlatSegment(p: Point) = flatSegments
      .find((s) => p.x > s.start.x && p.x < s.end.x && p.y - s.start.y < 1)
      .isDefined

  private def isUnderground(p: Point) =
    groundSegments.filter(new Ray(p, Point(0, 1)).intersects(_)).length % 2 == 1

  def simulate(dt: Double) {
    if (state == FLYING) {
      angle = clamp(-90.0 max (angle - 5), 90.0 min (angle + 5), desiredAngle)
      thrust = clamp(0 max (thrust - 1), 5 min (thrust + 1), desiredThrust)
      val force = angleToVector(angleRadian) * thrust + LanderWorld.GRAVITY
      position = position + speed * dt
      speed = speed + force * dt

      lazy val underground = isUnderground(position)
      lazy val goodConfig = speed.length < 10 && (angleRadian - PI/2) < 1e-2
      lazy val touchesFlat = touchesSomeFlatSegment(position)
      lazy val outOfWorldX = position.x < 0 || position.x > width
      lazy val outOfWorldY = position.y < 0 || position.y > height
      lazy val outOfWorld = outOfWorldX || outOfWorldY

      state =
        if (underground) {
          if (goodConfig && touchesFlat) LANDED
          else CRASHED
        } else {
          if (outOfWorld) OUT
          else FLYING
        }
    }
  }
}