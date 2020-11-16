package HoneypotAWS;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class HoneypotAttack {
	private Date datetime;
	private String host;
	private long src;
	private String proto;
	private int type;
	private int spt;
	private int dpt;
	private String srcstr;
	private String cc;
	private String country;
	private String locale;
	private String localeabbr;
	private String postalcode;
	private double latitude;
	private double longitude;
	private boolean missingData = false;

	public HoneypotAttack(String line) {
		// datetime,host,src,proto,type,spt,dpt,srcstr,cc,country,locale,localeabb,postalcode,latitude,longitude
		String[] values = line.split(",", -1);
		SimpleDateFormat ft = null;
		switch (Main.dataset) {
		case 0:
			ft = new SimpleDateFormat("dd/MM/yyyy HH:mm");
			break;
		case 1:
			ft = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			break;
		}
		try {
			this.datetime = ft.parse(values[0]);
		} catch (ParseException e) {
			System.out.println(values[0] + " is unparseable");
		}
		//Preforming a blank check as to assign default values if one is not submit
		this.host = blankCheckString(values[1], 0);
		this.src = blankCheckLong(values[2]);
		this.proto = blankCheckString(values[3], 0);
		this.type = blankCheckInt(values[4], 1);
		this.spt = blankCheckInt(values[5], 0);
		this.dpt = blankCheckInt(values[6], 0);
		this.srcstr = blankCheckString(values[7], 0);
		this.cc = blankCheckString(values[8], 0);
		this.country = blankCheckString(values[9], 0);
		this.locale = blankCheckString(values[10], 0);
		this.localeabbr = blankCheckString(values[11], 1);
		this.postalcode = blankCheckString(values[12], 1);
		this.latitude = blankCheckDouble(values[13]);
		this.longitude = blankCheckDouble(values[14]);
	}

	public void resetData(String line) {
		//datetime,host,src,proto,type,spt,dpt,srcstr,cc,country,locale,localeabb,postalcode,latitude,longitude
		//This method is used to override the current data when correct data is inseted
		//This method does not reset the date
		String[] values = line.split(",", -1);
		this.host = blankCheckString(values[1], 0);
		this.src = blankCheckLong(values[2]);
		this.proto = blankCheckString(values[3], 0);
		this.type = blankCheckInt(values[4], 1);
		this.spt = blankCheckInt(values[5], 0);
		this.dpt = blankCheckInt(values[6], 0);
		this.srcstr = blankCheckString(values[7], 0);
		this.cc = blankCheckString(values[8], 0);
		this.country = blankCheckString(values[9], 0);
		this.locale = blankCheckString(values[10], 0);
		this.localeabbr = blankCheckString(values[11], 1);
		this.postalcode = blankCheckString(values[12], 1);
		this.latitude = blankCheckDouble(values[13]);
		this.longitude = blankCheckDouble(values[14]);
	}
	
	private String blankCheckString(String i, int j) {
		if (!i.equals("")) {
			return i;
		} else {
			if (j == 0) {
				missingData = true;
			}
			return "N/A";
		}
	}

	private Integer blankCheckInt(String i, int j) {
		if (!i.equals("")) {
			return Integer.parseInt(i);
		} else {
			if (j == 0) {
				missingData = true;
			}
			return 0;
		}
	}

	private Long blankCheckLong(String i) {
		if (!i.equals("")) {
			return Long.parseLong(i);
		} else {
			missingData = true;
			return (long) 0;
		}
	}

	private Double blankCheckDouble(String i) {
		if (!i.equals("")) {
			return Double.parseDouble(i);
		} else {
			missingData = true;
			return 0.0;
		}
	}

	public String getAll() {
		DateFormat df = null;
		if(Main.dataset == 0) {
			df = new SimpleDateFormat("dd/MM/yyyy HH:mm");
		}else {
			df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		}
		//Making sure the date is format in the correct way for both files (sample and large)
		return df.format(datetime) + "," + host + "," + src + "," + proto + "," + type + "," + spt + "," + dpt + "," + srcstr + ","
				+ cc + "," + country + "," + locale + "," + localeabbr + "," + postalcode + "," + latitude + ","
				+ longitude;
	}

	public Date getDatetime() {
		return datetime;
	}

	public String getHost() {
		return host;
	}

	public long getSrc() {
		return src;
	}

	public String getProto() {
		return proto;
	}

	public int getType() {
		return type;
	}

	public int getSpt() {
		return spt;
	}

	public int getDpt() {
		return dpt;
	}

	public String getSrcstr() {
		return srcstr;
	}

	public String getCc() {
		return cc;
	}

	public String getCountry() {
		return country;
	}

	public String getLocale() {
		return locale;
	}

	public String getLocaleabbr() {
		return localeabbr;
	}

	public String getPostalcode() {
		return postalcode;
	}

	public double getLatitude() {
		return latitude;
	}

	public double getLongitude() {
		return longitude;
	}

	public boolean getMissingData() {
		return missingData;
	}
}
