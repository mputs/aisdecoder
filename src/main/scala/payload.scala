import scodec.bits._

object payload
{
	val Char_lookup = "@ABCDEFGHIJKLMNOPQRSTUVWXYZ[\\]^- !\"#$%&`()*+,-./0123456789:;<=>?"

	def istype(s: String):Int=
	{
		var y = s(0)-48;
		if(y>40) y-=8
		return y
	}
	def istype(b:BitVector):Int=
	{
		b.slice(0,6).toInt(false);
	}

	def convert6(x: Char): BitVector = 
	{
		var y = x-48;
		if (y>40) y -=8

		return BitVector(y).slice(2,8)
	}
	def getchar(b: BitVector):Char = 
	{
		return Char_lookup(b.toInt(false));
	}

	def getString(b:BitVector):String = 
	{
		var l:Long = b.length;
		var i:Long = 0;
		var s:String = "";

		while(i<l)
		{
			val x = b.slice(i, i+6);
			s += getchar(x);
			i += 6;
		}
		return s;
	}

	def getInt(b:BitVector, unsigned:Boolean=true):Int = 
	{
		
		return b.toInt(!unsigned);
	}
	def getDouble(b:BitVector, div:Double, unsigned:Boolean=true):Double = 
	{
		return b.toInt(!unsigned).toDouble/div;
	}
	
	def convert6string(x: String):BitVector = 
	{
		x.map(a=>convert6(a)).reduce(_++_)
	}
	val example = "14eG;o@034o8sd<L9i:a;WF>062D"
	case class msg123 (
		tpe: Int, 
		rpt: Int, 
		MMSI: Int, 
		navstat: Int,
		ROT: Int,
		SOG: Double,
		accuracy: Int,
		Lon: Double,
		Lat: Double,
		COG: Double,
		HDG: Int,
		TimeStamp: Int,
		Maneuvre: Int,
		Raim: Int,
		Radio: Int
	)
	def to123(b: BitVector):msg123=  
		msg123(
			getInt(b.slice(0,6)),
			getInt(b.slice(6,8)),
			getInt(b.slice(8,38)),
			getInt(b.slice(38,42)),
			getInt(b.slice(42,50),false),
			getDouble(b.slice(50,60),10),
			getInt(b.slice(60,61)),
			getDouble(b.slice(61,89),600000,false),
			getDouble(b.slice(89,116), 600000, false),
			getDouble(b.slice(116,128), 10),
			getInt(b.slice(128,137)),
			getInt(b.slice(137,143)),
			getInt(b.slice(143,145)),
			getInt(b.slice(148,149)),
			getInt(b.slice(149,168)) )
	case class msg4(
		tpe: Int, 
		rpt: Int, 
		MMSI: Int, 
		year: Int,
		month: Int,
		day: Int,
		hour: Int,
		minute: Int,
		second: Int,
		quality: Int,
		Lon: Double,
		Lat: Double,
		EPFDtype: Int,
		RAIM: Int,
		SOTDMA: Int);

	def to4(b: BitVector):msg4=  
		msg4(
			getInt(b.slice(0,6)),
			getInt(b.slice(6,8)),
			getInt(b.slice(8,38)),
			getInt(b.slice(38,52)),
			getInt(b.slice(52,56)),
			getInt(b.slice(56,61)),
			getInt(b.slice(61,66)),
			getInt(b.slice(66,72)),
			getInt(b.slice(72,78)),
			getInt(b.slice(78,79)),
			getDouble(b.slice(79,107),600000, false),
			getDouble(b.slice(107,134),600000, false),
			getInt(b.slice(134,138)),
			getInt(b.slice(148,149)),
			getInt(b.slice(149,168)))
	
	case class msg5(
		tpe: Int, 
		rpt: Int, 
		MMSI: Int, 
		ver: Int,
		IMO: Int,
		CallSign: String,
		VesselName: String,
		shiptype: Int,
		to_bow: Int,
		to_stern: Int,
		to_port: Int,
		to_starboard: Int,
		epfd: Int,
		month: Int,
		day: Int,
		hour: Int,
		minute: Int,
		draught: Double,
		Destination: String,
		dte: Int)
	def to5(b: BitVector):msg5=  
		msg5(
			getInt(b.slice(0,6)),
			getInt(b.slice(6,8)),
			getInt(b.slice(8,38)),
			getInt(b.slice(38,40)),
			getInt(b.slice(40,70)),
			getString(b.slice(70,112)),
			getString(b.slice(112,232)),
			getInt(b.slice(232,240)),
			getInt(b.slice(240,249)),
			getInt(b.slice(249,258)),
			getInt(b.slice(258,264)),
			getInt(b.slice(264,270)),
			getInt(b.slice(270,274)),
			getInt(b.slice(274,278)),
			getInt(b.slice(278,283)),
			getInt(b.slice(283,288)),
			getInt(b.slice(288,294)),
			getDouble(b.slice(294,302),10),
			getString(b.slice(302,422)),
			getInt(b.slice(422,423)))
}

