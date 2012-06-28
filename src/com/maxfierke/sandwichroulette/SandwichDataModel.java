package com.maxfierke.sandwichroulette;

import android.os.Parcel;
import android.os.Parcelable;

public class SandwichDataModel implements Parcelable {
	private long id;
	private String title;
	private int base;
	private int bread;
	private int cheese;
	private boolean pickles;
	private boolean onions;
	private boolean lettuce;
	private boolean tomato;
	private boolean grnPepper;
	private boolean spinach;
	private boolean cucumber;
	private boolean banPepper;
	private boolean olive;
	private boolean jalapeno;
	private boolean chipotle;
	private boolean hnyMustard;
	private boolean swtOnion;
	private boolean mayo;
	private boolean mustard;
	private boolean oil;
	private boolean vinaigrette;
	private boolean vinegar;
	private boolean italDressing;
	private boolean ranch;
	private boolean caesarDressing;
	private boolean hummus;
	// Number of boolean elements
	private final short NUM_INGREDIENTS = 22;

	public SandwichDataModel() {/* Nothing needs to be done here. Ever. */}
	
	public SandwichDataModel(Parcel in) {
		setId(in.readLong());
		setTitle(in.readString());
		setBase(in.readInt());
		setBread(in.readInt());
		setCheese(in.readInt());
		boolean data[] = new boolean[NUM_INGREDIENTS];
		in.readBooleanArray(data);
		setPickles(data[0]);
		setOnions(data[1]);
		setLettuce(data[2]);
		setTomato(data[3]);
		setGrnPepper(data[4]);
		setSpinach(data[5]);
		setCucumber(data[6]);
		setBanPepper(data[7]);
		setOlive(data[8]);
		setJalapeno(data[9]);
		setChipotle(data[10]);
		setHnyMustard(data[11]);
		setSwtOnion(data[12]);
		setMayo(data[13]);
		setMustard(data[14]);
		setOil(data[15]);
		setVinaigrette(data[16]);
		setVinegar(data[17]);
		setItalDressing(data[18]);
		setRanch(data[19]);
		setCaesarDressing(data[20]);
		setHummus(data[21]);
	}
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public int getBase() {
		return base;
	}

	public void setBase(int base) {
		this.base = base;
	}

	public int getBread() {
		return bread;
	}

	public void setBread(int bread) {
		this.bread = bread;
	}

	public int getCheese() {
		return cheese;
	}

	public void setCheese(int cheese) {
		this.cheese = cheese;
	}

	public boolean isPickles() {
		return pickles;
	}

	public void setPickles(boolean pickles) {
		this.pickles = pickles;
	}

	public boolean isOnions() {
		return onions;
	}

	public void setOnions(boolean onions) {
		this.onions = onions;
	}

	public boolean isLettuce() {
		return lettuce;
	}

	public void setLettuce(boolean lettuce) {
		this.lettuce = lettuce;
	}

	public boolean isTomato() {
		return tomato;
	}

	public void setTomato(boolean tomato) {
		this.tomato = tomato;
	}

	public boolean isGrnPepper() {
		return grnPepper;
	}

	public void setGrnPepper(boolean grnPepper) {
		this.grnPepper = grnPepper;
	}

	public boolean isSpinach() {
		return spinach;
	}

	public void setSpinach(boolean spinach) {
		this.spinach = spinach;
	}

	public boolean isCucumber() {
		return cucumber;
	}

	public void setCucumber(boolean cucumber) {
		this.cucumber = cucumber;
	}

	public boolean isBanPepper() {
		return banPepper;
	}

	public void setBanPepper(boolean banPepper) {
		this.banPepper = banPepper;
	}

	public boolean isOlive() {
		return olive;
	}

	public void setOlive(boolean olive) {
		this.olive = olive;
	}

	public boolean isJalapeno() {
		return jalapeno;
	}

	public void setJalapeno(boolean jalapeno) {
		this.jalapeno = jalapeno;
	}

	public boolean isChipotle() {
		return chipotle;
	}

	public void setChipotle(boolean chipotle) {
		this.chipotle = chipotle;
	}

	public boolean isHnyMustard() {
		return hnyMustard;
	}

	public void setHnyMustard(boolean hnyMustard) {
		this.hnyMustard = hnyMustard;
	}

	public boolean isSwtOnion() {
		return swtOnion;
	}

	public void setSwtOnion(boolean swtOnion) {
		this.swtOnion = swtOnion;
	}

	public boolean isMayo() {
		return mayo;
	}

	public void setMayo(boolean mayo) {
		this.mayo = mayo;
	}

	public boolean isMustard() {
		return mustard;
	}

	public void setMustard(boolean mustard) {
		this.mustard = mustard;
	}

	public boolean isOil() {
		return oil;
	}

	public void setOil(boolean oil) {
		this.oil = oil;
	}

	public boolean isVinaigrette() {
		return vinaigrette;
	}

	public void setVinaigrette(boolean vinaigrette) {
		this.vinaigrette = vinaigrette;
	}

	public boolean isVinegar() {
		return vinegar;
	}

	public void setVinegar(boolean vinegar) {
		this.vinegar = vinegar;
	}

	public boolean isItalDressing() {
		return italDressing;
	}

	public void setItalDressing(boolean italDressing) {
		this.italDressing = italDressing;
	}

	public boolean isRanch() {
		return ranch;
	}

	public void setRanch(boolean ranch) {
		this.ranch = ranch;
	}

	public boolean isCaesarDressing() {
		return caesarDressing;
	}

	public void setCaesarDressing(boolean caesarDressing) {
		this.caesarDressing = caesarDressing;
	}

	public boolean isHummus() {
		return hummus;
	}

	public void setHummus(boolean hummus) {
		this.hummus = hummus;
	}

	// Will be used by the ArrayAdapter in the ListView
	@Override
	public String toString() {
		return title;
	}
	
	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeLong(id);
		dest.writeString(title);
		dest.writeInt(base);
		dest.writeInt(bread);
		dest.writeInt(cheese);
		dest.writeBooleanArray(new boolean[]{
			pickles,
			onions,
			lettuce,
			tomato,
			grnPepper,
			spinach,
			cucumber,
			banPepper,
			olive,
			jalapeno,
			chipotle,
			hnyMustard,
			swtOnion,
			mayo,
			mustard,
			oil,
			vinaigrette,
			vinegar,
			italDressing,
			ranch,
			caesarDressing,
			hummus
		});
	}
	
	public static final Parcelable.Creator<SandwichDataModel> CREATOR= new Parcelable.Creator<SandwichDataModel>() {
		@Override
		public SandwichDataModel createFromParcel(Parcel source) {
			return new SandwichDataModel(source);  //using parcelable constructor
		}
	 
		@Override
		public SandwichDataModel[] newArray(int size) {
			return new SandwichDataModel[size];
		}
	};
}
