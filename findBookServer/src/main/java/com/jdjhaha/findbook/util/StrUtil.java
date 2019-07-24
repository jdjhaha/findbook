package com.jdjhaha.findbook.util;

import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import java.util.List;

public class StrUtil {

	public StrUtil() {

	}

	/**
	 * 문자열을 HTML 출력용으로 변환한다.
	 * 
	 * @param str
	 *            문자열
	 * @return 문자열
	 */
	public static String getHTMLString( String str ) {

		str = replace( str, "<", "&lt;" );
		str = replace( str, ">", "&gt;" );
		str = replace( str, "\"", "&quot;" );
		str = replace( str, "\n", "<br>\n" );
		str = replace( str, "  ", " &nbsp;" );
		str = replace( str, "\t", " &nbsp; &nbsp;" );

		return str;
	}

	/**
	 * 문자열의 일부분을 다른 문자열로 치환한다.
	 * 
	 * @param argSource
	 *            원본 문자열
	 * @param argQuery
	 *            문자열에서 찾을 패턴
	 * @param argReplace
	 *            치환할 문자열
	 * @return 치환된 문자열
	 */
	public static String replace( String argSource, String argQuery, String argReplace ) {
		// original code by John Fries

		if( argSource == null || argQuery == null || argReplace == null )
			return argSource;

		int index = 0;
		int iQueryLen = argQuery.length();
		int iStart = 0;
		StringBuffer sb = new StringBuffer();
		// find first occurance of query
		index = argSource.indexOf( argQuery );
		while( index > -1 ) {
			// append front half before query and replacement
			sb.append( argSource.substring( iStart, index ) );
			sb.append( argReplace );
			// try to find next occurance searching after last occurance
			iStart = index + iQueryLen;
			index = argSource.indexOf( argQuery, iStart );
		}

		// the while loop will exit when nothing is found, so append rest of
		// source
		sb.append( argSource.substring( iStart, argSource.length() ) );
		return sb.toString();
	}

	public static String NVL( Object o ) {

		if( o == null )
			return "";
		else
			return ( String.valueOf( o ) ).trim();
	}

	public static String NVL( String s ) {

		if( s == null || s.equals( "" ) )
			return "";
		else
			return s.trim();
	}

	public static String NVLR( String s ) {

		if( s == null || s.equals( "" ) )
			return "";
		else {
			String retData = s.replaceAll( "\"", "\'" ).trim();
			retData = retData.replaceAll( "\n", "" );
			retData = retData.replaceAll( "\r", "" );
			return retData;
		}
	}

	public static String NVLR( String s, String target, String replace ) {

		if( s == null || s.equals( "" ) )
			return "";
		else
			return s.replaceAll( target, replace ).trim();
	}

	public static String NVL( byte[] s ) {

		if( s == null ) {
			return ( "" );
		} else {
			if( s.length > 0 ) {
				return new String( s );
			} else {
				return ( "" );
			}
		}
	}

	public static String NVL( long s ) {

		if( s == 0L ) {
			return ( "" );
		} else {
			return ( Long.toString( s ) );
		}
	}

	public static String NVL( long s, String r ) {

		if( s == 0L ) {
			return ( r );
		} else {
			return ( Long.toString( s ) );
		}
	}

	public static String NVL( Object o, String s ) {

		if( o == null )
			return s;
		else
			return ( String.valueOf( o ) ).trim();
	}

	public static String NVL( String s, String s1 ) {

		if( s == null || s.equals( "" ) )
			return s1;
		else
			return s;
	}

	public static int NVLtint( String s ) {

		if( s == null || s.equals( "" ) )
			return 0;
		else
			return Integer.parseInt( s );
	}

	public static int NVLtint( String s, int i ) {

		if( s == null || s.equals( "" ) )
			return i;
		else
			return Integer.parseInt( s );
	}

	public static byte[] NVLtb( String s ) {

		if( s == null || s.equals( "" ) )
			return new byte[1];
		else
			return ( s.getBytes() );
	}

	public static long NVLtlong( String s ) {

		if( s == null || s.equals( "" ) )
			return ( long )0;
		else
			return Long.parseLong( s );
	}

	public static long NVLtlong( String s, long i ) {

		if( s == null || s.equals( "" ) )
			return i;
		else
			return Long.parseLong( s );
	}

	public static boolean NVLtboolean( String s ) {

		if( s == null || s.equals( "" ) )
			return true;
		else
			return Boolean.getBoolean( s );
	}

	public static String NVLtTime( String s ) {

		if( s == null || s.equals( "" ) )
			return "--년--월--일 --시--분--초";
		else
			return s.replaceFirst( "-", "년" ).replaceFirst( "_", "월" ).replaceFirst( "/", "일" ).replaceFirst( ":", "시" ).replaceFirst( "@", "분" )
					.replaceFirst( "#", "초" );
	}

	public static String NVLdTime( String s ) {

		if( s == null || s.equals( "" ) )
			return "--년--월--일 --시--분--초";
		else
			return s.replaceFirst( "/", "년" ).replaceFirst( "/", "월" ).replaceFirst( " ", "일" ).replaceFirst( ":", "시" ).replaceFirst( ":", "분" )
					+ "초";
	}

	public static String NVLTK( String s ) {

		try {
			if( s == null || s.equals( "" ) )
				return "";
			else
				return new String( s.getBytes( "8859_1" ), "UTF-8" );
		} catch( UnsupportedEncodingException e ) {
			return ( "" );
		}
	}

	public static String NVLTK( String s, String f1, String f2 ) {

		try {
			if( s == null || s.equals( "" ) )
				return "";
			else
				return new String( s.getBytes( f1 ), f2 );
		} catch( UnsupportedEncodingException e ) {
			return ( "" );
		}
	}

	public static int getCount( String src, String tg ) {

		int idx = 0;
		int cnt = 0;
		try {
			while( true ) {
				idx = src.indexOf( tg, idx );

				if( idx < 0 )
					break;
				else {
					cnt++;
					idx++;
				}
			}
		} catch( Exception e ) {
			return 0;
		}

		return cnt;
	}

	public static String replaceAll( String src, String target, String replace ) {

		try {
			int pos = 0;
			int idx = 0;
			int t_len = target.length();
			int s_len = src.length();
			StringBuffer ret = new StringBuffer();
			while( true ) {
				idx = src.indexOf( target, pos );
				if( idx < 0 )
					break;

				ret.append( src.substring( pos, idx ) );
				ret.append( replace );
				pos = idx + t_len;
			}
			if( pos < s_len )
				ret.append( src.substring( pos, s_len ) );
			return ret.toString();
		} catch( Exception e ) {
			e.printStackTrace();
			return src;
		}
	}

	public static StringBuffer replaceAll( StringBuffer src, String target, String replace ) {

		try {
			int pos = 0;
			int idx = 0;
			int t_len = target.length();
			int s_len = src.length();
			StringBuffer ret = new StringBuffer();
			while( true ) {
				idx = src.indexOf( target, pos );
				if( idx < 0 )
					break;

				ret.append( src.substring( pos, idx ) );
				ret.append( replace );
				pos = idx + t_len;
			}
			if( pos < s_len )
				ret.append( src.substring( pos, s_len ) );
			return ret;
		} catch( Exception e ) {
			e.printStackTrace();
			return src;
		}
	}

	public static String replaceFirst( String src, String target, String replace ) {

		if( src == null || target == null || replace == null )
			return src;

		try {
			int idx = 0;
			int warp = target.length();

			idx = src.indexOf( target );

			if( idx < 0 )
				return src;

			return src.substring( 0, idx ) + replace + src.substring( idx + warp );

		} catch( Exception e ) {
			return src;
		}
	}

	public static String getTokenizer( String inputText, int selIdx ) {

		String retStr = "";
		String[] ss = inputText.split( "[\u007C]" );

		for( int i = 0; i < ss.length; i++ ) {
			if( selIdx == i ) {
				retStr = ss[i];
			} else {
				continue;
			}
		}

		return retStr;

	}

	public static String getTokenizerDot( String inputText, int stIdx, int enIdx ) {

		String retStr = "";
		String[] ss = inputText.split( "[\u007C]" );

		if( ss.length > 1 ) {

			for( int i = 0; i < ss.length; i++ ) {
				if( stIdx == i ) {
					retStr = ss[i];
				} else if( enIdx == i ) {
					retStr += "." + ss[i];
				}
				continue;
			}
		}

		return retStr;
	}

	public static String decodeXML( String sData ) {

		String[] before = { "&amp;", "&quot;", "&#39;", "&lt;", "&gt;" };
		String[] after = { "&", "\"", "\'", "<", ">" };

		if( sData != null && !"".equals( sData ) ) {
			for( int i = 0; i < before.length; i++ ) {
				sData = replace( sData, before[i], after[i] );
			}
		} else {
			sData = " ";
		}

		return sData;
	}

	public static String encodeXML( String sData ) {

		String[] before = { "&", "\"", "\'", "<", ">" };
		String[] after = { "&amp;", "&quot;", "&#39;", "&lt;", "&gt;" };

		if( sData != null ) {
			for( int i = 0; i < before.length; i++ ) {
				sData = replace( sData, before[i], after[i] );
			}
		} else {
			sData = " ";
		}

		return sData;
	}

	public static byte[] convertByteArray( String sData, String encoding ) {

		byte[] bData = null;

		try {
			bData = sData.getBytes( encoding );
		} catch( UnsupportedEncodingException e ) {
			e.printStackTrace();
		}

		return bData;
	}

	public static String convertString( byte[] bData, String encoding ) {

		String sData = "";

		try {
			sData = new String( bData, encoding );
		} catch( UnsupportedEncodingException e ) {
			e.printStackTrace();
		}

		return sData;
	}

	public static boolean isEmpty( Object str ) {

		return str == null || "".equals( str );
	}

	public static boolean isNotEmpty( Object str ) {

		return !isEmpty( str );
	}

	public static boolean isEqual( String str1, String str2 ) {

		return str1.equals( str2 );
	}

	public static boolean isNotEqual( String str1, String str2 ) {

		return !isEqual( str1, str2 );
	}

	public static String arrayToString( String[] stringArray, String delimiter ) {

		if( stringArray != null && stringArray.length > 0 ) {
			StringBuilder sb = new StringBuilder();

			for( String str : stringArray ) {
				sb.append( str ).append( delimiter );
			}

			sb.deleteCharAt( sb.length() - 1 );

			return sb.toString();
		} else {
			return "";
		}
	}

	public static String[] split( String str, String separatorChars ) {

		if( str == null ) {
			return null;
		} else {
			return str.split( separatorChars );
		}
	}

	public static List<String> splitToList( String str, String separatorChars ) {

		if( str == null ) {
			return null;
		} else {
			return Arrays.asList( str.split( separatorChars ) );
		}
	}

	public static String getHangul( String str ) {

		if( str == null || "".equals( str ) ) {
			return "";
		} else {
			return str.replaceAll( "[^\uAC00-\uD7AF\u1100-\u11FF\u3130-\u318F]", "" );
		}
	}
}