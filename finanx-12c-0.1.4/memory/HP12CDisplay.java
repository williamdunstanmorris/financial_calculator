package memory;

import java.util.Locale;

import core.HP12CInputException;

import static core.HP12CFunctions.fitToHP12C;

public class HP12CDisplay extends DisplayAbstraction {

	public static final int STATUS_OUTPUT2 = 3;

	private static String ZEROFILL = "0000000000";

	private String bf[];

	public HP12CDisplay() {
		super();
	}

	public void init() { /* It does nothing. */
	}

	public void inputChar(char ch) {

		if (lock || pause) {
			lock = false;
			pause = false;
			return;
		}

		if (this.mode == MODE_NORMAL) {

			// Changing the sign between positive and negative
			// It only changes the sign and returns.
			if (ch == '-') {
				neg = !neg;
				return;
			} else {
				if (!(status == STATUS_INPUT)) {
					setReady();
				}
			}

			// If the buffer is full, return.
			if (full)
				return;

			// Inserting decimal point
			// It only insert a point if needed and returns
			if (ch == '.') {
				if (!dot) {
					if (!(status == STATUS_INPUT)) {
						dot = true;
						status = HP12CDisplay.STATUS_INPUT;
						buf = "0.";
						return;
					} else {
						dot = true;
						buf += ".";
						return;
					}
				} else {
					return;
				}
			} else {
				// Inserting numbers
				if (!(status == STATUS_INPUT)) {

					if (ch == '0') {
						buf = "0"; // Ignore zeros in left size.
						status = HP12CDisplay.STATUS_INPUT;
						return;
					} else {
						buf = "" + ch;
						status = HP12CDisplay.STATUS_INPUT;
					}
				} else {
					buf += "" + ch;
					if (!dot)
						buf = "" + Long.parseLong(buf); // Ignore zeros in the
														// left side of integer
														// part
				}
			}

			// Checks if the buffer is full
			if (dot) {
				if (buf.length() >= 11) {
					full = true;
					return;
				}
			} else {
				if (buf.length() >= 10) {
					full = true;
					return;
				}
			}

		} else if (this.mode == MODE_EXPONENTIAL) {
			if (ch == '-') {
				// Switching base sign between - and +
				if (!(status == STATUS_INPUT)) {
					neg = !neg;
					return;
				}
				// Switching exponent sign between - and +
				else {
					expo = -(expo);
					return;
				}
			}
			// Resetting the other flags.
			// It is necessary to input new values;
			else {
				if (!(status == STATUS_INPUT)) {
					setReady();
					inputChar(ch);
					return;
				}
			}

			if (expo == 0) {
				expo = Integer.parseInt("" + ch);
			} else if ((expo > 0) && (expo < 10)) {
				expo *= 10; // Shifting to left
				expo += Integer.parseInt("" + ch);
			} else if ((expo < 0) && (expo > -10)) {
				expo *= 10; // Shifting to left
				expo -= Integer.parseInt("" + ch);
			} else if (expo >= 10) {
				expo *= 10; // Shifting to left
				expo %= 100;
				expo += Integer.parseInt("" + ch);
			} else if (expo <= -10) {
				expo *= 10; // Shifting to left
				expo %= 100;
				expo -= Integer.parseInt("" + ch);
			}
		}
	}

	public void inputProgramStep(int idx, HP12CStep stp) {

		this.stp = new int[4];

		this.stp[0] = idx;
		this.stp[1] = stp.getModifier();
		this.stp[2] = stp.getKey();
		this.stp[3] = stp.getComplement();

	}

	public String getString() {
		if (lock || pause) {
			return " " + this.msg;
		}

		if (mode != MODE_PROGRAM) {
			if (status != STATUS_INPUT) {
				updateValue();
				if (((Math.abs(val) > 1e-10) && (Math.abs(val) < 1e10))
						|| (val == 0.0)) {
					getNormalString();
				} else {
					getExponentialString();
				}
			} else {
				if (mode == MODE_NORMAL) {
					getNormalString();
				} else if (mode == MODE_EXPONENTIAL) {
					getExponentialString();
				}
			}
		} else {
			getProgramString();
		}

		return str;
	}

	// Adds group and decimal separators (dots and commas)
	private void digitSeparators() {

		int grpcount = 0;
		char grpchar = ','; // group separator
		char decchar = '.'; // decimal separator

		// Separates the integer part from the fractional part
		String tmp[] = str.split("\\.");

		// Change the digit separators if necessary
		if (this.comma) {
			grpchar = '.';
			decchar = ',';
		}

		// Adding group separators in a reverse string
		String revstr = "";
		for (int i = tmp[0].length() - 1; i >= 0; --i) {
			grpcount++;

			if (grpcount == 3) {
				revstr += tmp[0].charAt(i);
				if (i > 1)
					revstr += grpchar;
				grpcount = 0;
			} else {
				revstr += tmp[0].charAt(i);
			}
		}

		// Reversing the reverse string
		char newstr[] = new char[revstr.length()];
		for (int i = 0; i < revstr.length(); i++) {
			newstr[newstr.length - 1 - i] = revstr.charAt(i);
		}

		// Joining integer part and fractional part again.
		this.str = new String(newstr);
		this.str = this.str + decchar + (tmp.length > 1 ? tmp[1] : "");

	}

	private void getExponentialString() {
		if (!(status == STATUS_INPUT)) {

			val = Double.parseDouble(buf + "e" + expo);
			str = "" + val;

			dot = true;
			bf = str.split("E");

			buf = String.format(Locale.ENGLISH, "%1." + prec + "f",
					Double.parseDouble(bf[0]));
			expo = (bf.length == 2) ? Integer.parseInt(bf[1]) : 0;

		}

		bf = new String[2];
		bf[0] = buf;

		if (dot) {
			if (buf.length() < 8) {
				for (int i = buf.length(); i < 8; i++) {
					bf[0] += " ";
				}
			} else {
				bf[0] = bf[0].substring(0, 8);
			}
		} else {
			if (buf.length() < 7) {
				bf[0] = bf[0] + ".";
				for (int i = buf.length(); i < 7; i++) {
					bf[0] += " ";
				}
			} else {
				bf[0] = bf[0].substring(0, 7);
				bf[0] = bf[0] + ".";
			}
		}

		bf[0] = bf[0] + (expo >= 0 ? " " : "-");

		if (Math.abs(expo) < 10)
			bf[1] = "0" + (int) Math.abs(expo);
		else
			bf[1] = "" + (int) Math.abs(expo);

		str = bf[0] + bf[1];

		str = (neg ? "-" : " ") + str; // Change signal

		digitSeparators();

	}

	private void getNormalString() {
		if (!(status == STATUS_INPUT)) {

			str = String.format(Locale.ENGLISH, "%1." + prec + "f",
					Double.parseDouble(buf + "E" + expo));

			if (str.length() > 11) {
				bf = str.split("\\.");
				str = String.format(Locale.ENGLISH,
						"%1." + (10 - bf[0].length()) + "f",
						Double.parseDouble(buf + "E" + expo));
			}

			bf = str.split("\\.");
			str = bf[0] + ".";
			str = (neg ? "-" : " ") + str + (bf.length == 2 ? bf[1] : ""); // Change
																			// signal
		} else {
			if (dot) {
				str = (neg ? "-" : " "); // Change signal
				str += buf;
			} else {
				str = (neg ? "-" : " "); // Change signal
				str += buf + ".";
			}
		}

		digitSeparators();

	}

	private void getProgramString() {

		int i = this.stp[0];
		int m = this.stp[1];
		int k = this.stp[2];
		int c = this.stp[3];

		if (m > (-1) && k > (-1) && c > (-1)) {
			if (k == 33)
				str = "" + zeroPad(i, 3) + "-" + spacePad(m, 2) + ","
						+ spacePad(k, 2) + "," + zeroPad(c, 3);
			else
				str = "" + zeroPad(i, 3) + "-" + spacePad(m, 2) + ","
						+ spacePad(k, 2) + ","
						+ (c > 99 ? zeroPad(c, 3) : "r" + zeroPad(c, 2));
		} else if (k > (-1) && c > (-1)) {
			str = "" + zeroPad(i, 3) + "-" + "  " + spacePad(k, 2) + ","
					+ (c > 99 ? zeroPad(c, 3) : "r" + zeroPad(c, 2));
		} else if (m > (-1) && k > (-1)) {
			str = "" + zeroPad(i, 3) + "-" + "  " + spacePad(m, 2) + ","
					+ spacePad(k, 3);
		} else if (k > (-1)) {
			str = "" + zeroPad(i, 3) + "-" + "  " + "  " + spacePad(k, 3);
		} else {
			str = "" + zeroPad(i, 3) + "-" + "  " + "  " + zeroPad(0, 3);
		}

	}

	public String getMantissa() {
		String rtn = "";

		bf = buf.split("\\.");
		rtn = bf[0] + (bf.length == 2 ? bf[1] : "");
		rtn = rtn + ZEROFILL;
		if (rtn.length() > 10)
			rtn = rtn.substring(0, 10);

		return rtn;
	}

	public void setValue(double val) {
		this.val = val;
		if (((Math.abs(val) > 1e-10) && (Math.abs(val) < 1e10)) || (val == 0.0)) {
			buf = String.format(Locale.ENGLISH, "%10.9f", Math.abs(val));
			expo = 0;
			neg = (val < 0 ? true : false);
		} else {
			str = "" + Math.abs(val);
			bf = str.split("E");
			buf = bf[0];
			expo = (bf.length == 2) ? Integer.parseInt(bf[1]) : 0;
			neg = (val < 0 ? true : false);
		}
	}

	public double getValue() throws HP12CInputException {
		updateValue();
		return fitToHP12C(val);
	}

	public void updateValue() {
		try {

			bf = new String[2];

			if (expo != 0) {
				bf[0] = buf + "E" + expo;
				val = Double.parseDouble(bf[0]);
			} else {
				bf[0] = buf;
				val = Double.parseDouble(bf[0]);
			}

			if (neg)
				val = -(val);
		} catch (NumberFormatException e) {
		} // TODO
	}

	public void setReady() {
		mode = MODE_NORMAL;
		dot = false;
		neg = false;
		full = false;
		buf = "";
		bf = null;
		expo = 0;
		val = 0.0;
	}

	public static String zeroPad(int val, int size) {

		String tmp = "";
		int v = ("" + val).length();

		if (v < size) {
			for (int i = 0; i < size - v; i++) {
				tmp += '0';
			}

			tmp += "" + val;

		} else {
			tmp += "" + val;
		}

		return tmp;
	}

	public static String spacePad(int val, int size) {

		String tmp = "";
		int v = ("" + val).length();

		if (v < size) {
			for (int i = 0; i < size - v; i++) {
				tmp += ' ';
			}

			tmp += "" + val;

		} else {
			tmp += "" + val;
		}

		return tmp;
	}

}
