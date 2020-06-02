/*
 * Copyright (c) 2018 DarkCompet. All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package tool.compet.core.util;

import android.content.Context;

import java.util.Iterator;
import java.util.Locale;

/**
 * 本クラス、文字列処理をサポートします。
 */
public class DkStrings {
	public static boolean isWhite(String s) {
		return s == null || s.trim().length() == 0;
	}

	public static boolean isEmpty(CharSequence cs) {
		return cs == null || cs.length() == 0;
	}

	public static void requireNotEmpty(String data, String message) {
		if (isEmpty(data)) {
			throw new RuntimeException(message);
		}
	}

	/**
	 * @return 0 (if equals), -1 (if a < b), 1 (if a > b).
	 */
	public static int compare(CharSequence a, CharSequence b) {
		if (a == null) {
			return b == null ? 0 : -1;
		}
		if (b == null) {
			return 1;
		}

		final int M = a.length();
		final int N = b.length();

		if (M != N) {
			return M < N ? -1 : 1;
		}

		char ch1, ch2;

		for (int i = 0; i < N; ++i) {
			ch1 = a.charAt(i);
			ch2 = b.charAt(i);

			if (ch1 < ch2) {
				return -1;
			}
			if (ch1 > ch2) {
				return 1;
			}
		}
		return 0;
	}

	/**
	 * Remove start-leading and end-leading WHITESPACE characters and characters in targets from msg.
	 */
	public static String trimExtras(String msg, char... extras) {
		if (msg == null || msg.length() == 0) {
			return msg;
		}

		boolean fromLeft = true;
		boolean fromRight = true;
		final boolean shouldCheckTargets = (extras != null);
		final int N = msg.length();
		int startIndex = 0, endIndex = N - 1;

		while (startIndex <= endIndex && (fromLeft || fromRight)) {
			// Check from left to right
			if (fromLeft) {
				char current = msg.charAt(startIndex);
				boolean stopCheck = true;

				// Check whether character insides targets
				if (shouldCheckTargets) {
					for (int i = extras.length - 1; i >= 0; --i) {
						if (current == extras[i]) {
							stopCheck = false;
							++startIndex;
							break;
						}
					}
				}
				// Stop checking whitespace since found this character in targets
				if (stopCheck) {
					fromLeft = false;
				}
				// Check whether the character is whitespace
				else if (Character.isWhitespace((int) current)) {
					++startIndex;
				}
			}

			// Check from right to left
			if (fromRight) {
				char current = msg.charAt(endIndex);
				boolean stopCheck = true;

				// Check whether the character insides targets
				if (shouldCheckTargets) {
					for (int i = extras.length - 1; i >= 0; --i) {
						if (current == extras[i]) {
							stopCheck = false;
							--endIndex;
							break;
						}
					}
				}
				// Stop checking whitespace since found this character in targets
				if (stopCheck) {
					fromRight = false;
				}
				// Check whether the character is whitespace
				else if (Character.isWhitespace((int) current)) {
					--endIndex;
				}
			}
		}

		return (endIndex < startIndex) ? "" : msg.substring(startIndex, endIndex + 1);
	}

	/**
	 * Remove start-leading and end-leading characters inside ONLY targets from msg.
	 */
	public static String trimExact(String msg, char... targets) {
		if (msg == null || msg.length() == 0 || targets == null || targets.length == 0) {
			return msg;
		}

		boolean fromLeft = true;
		boolean fromRight = true;
		final int N = msg.length();
		int startIndex = 0, endIndex = N - 1;

		while (startIndex <= endIndex && (fromLeft || fromRight)) {
			// check from left to right
			if (fromLeft) {
				char current = msg.charAt(startIndex);
				boolean stopCheck = true;
				// check whether current insides targets
				for (int i = targets.length - 1; i >= 0; --i) {
					if (current == targets[i]) {
						stopCheck = false;
						++startIndex;
						break;
					}
				}
				if (stopCheck) {
					fromLeft = false;
				}
			}
			// check from right to left
			if (fromRight) {
				char current = msg.charAt(endIndex);
				boolean stopCheck = true;
				// check whether current insides targets
				for (int i = targets.length - 1; i >= 0; --i) {
					if (current == targets[i]) {
						stopCheck = false;
						--endIndex;
						break;
					}
				}

				if (stopCheck) {
					fromRight = false;
				}
			}
		}

		return (endIndex < startIndex) ? "" : msg.substring(startIndex, endIndex + 1);
	}

	public static boolean isEquals(CharSequence a, CharSequence b) {
		return a == b || (a != null && a.equals(b));
	}

	public static String join(char delimiter, String... items) {
		StringBuilder sb = new StringBuilder();
		boolean first = true;
		for (String item : items) {
			if (first) {
				first = false;
			}
			else {
				sb.append(delimiter);
			}
			sb.append(item);
		}
		return sb.toString();
	}

	public static String join(char delimiter, Iterable<String> items) {
		StringBuilder sb = new StringBuilder();
		Iterator<String> it = items.iterator();

		if (it.hasNext()) {
			sb.append(it.next());

			while (it.hasNext()) {
				sb.append(delimiter);
				sb.append(it.next());
			}
		}
		return sb.toString();
	}

	public static String join(CharSequence delimiter, Iterable<String> items) {
		StringBuilder sb = new StringBuilder();
		Iterator<String> it = items.iterator();

		if (it.hasNext()) {
			sb.append(it.next());

			while (it.hasNext()) {
				sb.append(delimiter);
				sb.append(it.next());
			}
		}
		return sb.toString();
	}

	public static String join(CharSequence delimiter, String... items) {
		StringBuilder sb = new StringBuilder();
		boolean first = true;

		for (String item : items) {
			if (first) {
				first = false;
			}
			else {
				sb.append(delimiter);
			}
			sb.append(item);
		}

		return sb.toString();
	}

	public static String format(Context context, int format, Object... args) {
		return format(context.getString(format), args);
	}

	public static String format(String format, Object... args) {
		return format == null || (args == null || args.length == 0) ? format : String.format(Locale.US, format, args);
	}
}
