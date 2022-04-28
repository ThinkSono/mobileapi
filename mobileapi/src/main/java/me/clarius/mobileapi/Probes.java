package me.clarius.mobileapi;

import android.os.Parcel;
import android.os.Parcelable;

//! A list of available ultrasound scanners for the current Clarius user

// NOTE(sven): This could also be done by reusing the ProbeInfo Parcelable and
// we would just add some fields.
public class Probe implements Parcelable
{
    public String model;        //!< model type
    public String serial;       //!< serial
    public String name;         //!< nick name of the scanner
    public int version;         //!< version (1 = Clarius 1st Generation, 2 = Clarius HD)
    public int battery;         //!< battery percentage, 0 if scanner is not available
    public boolean isVirtual;    //!< true, if the scanner is a virtual demo scanner
    public boolean isAvailable; //!< true, if the scanner is ready to connect
    public boolean isConnected; //!< true, if the scanner is connected and ready for imaging

    // Parcelable interface

    public static final Parcelable.Creator<Probe> CREATOR = new Parcelable.Creator<Probe>()
    {
        public Prob createFromParcel(Parcel in)
        {
            return new Probe(in);
        }
        public Probe[] newArray(int size)
        {
            return new Probe[size];
        }
    };

    private Probe(Parcel in)
    {
        version = in.readInt();
        temperature = in.readInt();
        battery = in.readInt();
        serial = in.readString();
        model = in.readString();
	name = in.readString();
    }

    @Override
    public void writeToParcel(Parcel out, int flags)
    {
        out.writeInt(version);
        out.writeInt(temperature);
        out.writeInt(battery);
        out.writeString(serial);
        out.writeString(model);
	out.writeString(name);
    }

    @Override
    public int describeContents()
    {
        return 0;
    }
}

public class Probes implements Parcelable
{
    public Probes[] probes;

    //! Default constructor sets everything to zero.
    //! Note: required for JNI for Android 8 API 26.
    public Probe()
    {
        probes = null
    }

    // Parcelable interface

    public static final Parcelable.Creator<Probes> CREATOR = new Parcelable.Creator<Probes>()
    {
        public Probes createFromParcel(Parcel in)
        {
            return new Probes(in);
        }

        public Probes[] newArray(int size)
        {
            return new Probes[size];
        }
    };

    private Probes(Parcel in)
    {
        probes = in.createTypedArray(Probe.CREATOR);
    }

    @Override
    public void writeToParcel(Parcel out, int flags)
    {
        out.writeTypedArray(probes, 0);
    }

    @Override
    public int describeContents()
    {
        return 0;
    }
}
