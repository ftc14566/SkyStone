package org.firstinspires.ftc.teamcode.bravo;

import java.util.ArrayList;
import java.util.Dictionary;
import java.util.Hashtable;

public class JsonParser {

    public JsonParser(String json){
        _json = json;
    }

    public Object DeserializeJsonVal() {
        EatWhiteSpace();
        char next = SafePeekChar();
        switch( next ) {
            case 'n':
                ReadNullIfThere();
                return null;

            case '[':
                return this.DeserializeArray();

            case '{':
                return this.DeserializeDictionary();

            case 't':
            case 'f':
                return this.DeserializeBoolean(false);

            case '\'':
            case '"':
                return this.DeserializeString();

            case '0':
            case '1':
            case '2':
            case '3':
            case '4':
            case '5':
            case '6':
            case '7':
            case '8':
            case '9':
            case '-':
            case '.':
                return this.DeserializeNumber(false);

            default:
                throw new IllegalStateException( "Deserialize() encountered unexpected character: " + next );
        }
    }



    public Double DeserializeNumber(boolean allowNulls) {
        EatWhiteSpace();
        if( ReadNullIfThere() ) {
            if( allowNulls ) return null;
            throw new IllegalStateException("Found null instead of number.");
        }

        int start = _index;

        if(SafePeekChar()=='-') ++_index; // negative?
        while(isDigit(SafePeekChar())) ++_index; // whole #
        if( SafePeekChar() == '.' ) {	// has decimal
            ++_index;
            while( isDigit(SafePeekChar() ) ) ++_index; // get decimal part
        }

        if(SafePeekChar()=='E') {
            ++_index;
            if(SafePeekChar()=='-') ++_index;
            int startOfE = _index;
            while( isDigit(SafePeekChar())) ++_index;
            if( _index == startOfE ) throw new IllegalStateException("Number in scientific form but missing exponent.");
        }

        return Double.parseDouble(_json.substring(start,_index-start));
    }

    static boolean isDigit(char k){ return '0'<=k&&k<='9'; }

    boolean IsQuote(char k) { return k=='\'' || k=='"'; }

    public String DeserializeString() {
        EatWhiteSpace();
        if( ReadNullIfThere() ) return null;

        char startChar = SafePeekChar();
        if( !IsQuote(startChar)) throw new IllegalStateException("Did not find starting quote of string.");
        UnsafeReadChar();

        StringBuilder buf = new StringBuilder();
        char k;
        while( (k = ReadAnyCharOrThrowExceptionIfAtEnd()) != startChar ) {
            // handle normal case
            if( k != '\\' ) {
                buf.append( k );
                continue;
            }
            // escape characters
            k = ReadAnyCharOrThrowExceptionIfAtEnd();
            switch( k ) {
                case 'b': k = '\b'; break;
                case 'f': k = '\f'; break;
                case 'n': k = '\n'; break;
                case 'r': k = '\r'; break;
                case 't': k = '\t'; break;
//                case 'u': k = (char)int.Parse( ReadString( 4 ), System.Globalization.NumberStyles.HexNumber ); break;
                default: break; // no action
            }
            buf.append( k );
        }
        return buf.toString();
    }

    public ArrayList DeserializeArray() {
        EatWhiteSpace();
        if( ReadNullIfThere() ) return null;
        ArrayList jsonArray = new ArrayList();

        ReadSpecificChar( '[' );
        EatWhiteSpace();
        if( SafePeekChar() != ']' )
            do {
                jsonArray.add( this.DeserializeJsonVal() );
            } while( TryReadChar( ',' ) );
        ReadSpecificChar( ']' );
        return jsonArray;
    }

    public Dictionary<String,Object> DeserializeDictionary() {
        EatWhiteSpace();
        if( ReadNullIfThere() ) return null;

        Hashtable<String,Object> jsonObject = new Hashtable<String,Object>();

        ReadSpecificChar( '{' );
        EatWhiteSpace();
        if( SafePeekChar() != '}' ) {
            do {
                String key = this.DeserializeString();
                ReadSpecificChar( ':' );
                Object val = this.DeserializeJsonVal();
                jsonObject.put( key, val );

            } while( TryReadChar( ',' ) );
        }
        ReadSpecificChar( '}' );
        return jsonObject;
    }

    public Boolean DeserializeBoolean(boolean allowNulls) {
        EatWhiteSpace();
        if(allowNulls && ReadNullIfThere() ) return null;
        switch( SafePeekChar() ) {
            case 't': ReadString( "true" ); return true;
            case 'f': ReadString( "false" ); return false;
            default: throw new IllegalStateException( "invalid boolean value" );
        }
    }

    char SafePeekChar() { return AtEnd() ? '\0' : _json.charAt(_index); } // if we are at the end, '\0' is returned
    char UnsafeReadChar() { return _json.charAt(_index++); }// the index is not checked here, caller must check index before calling this.
    boolean AtEnd(){ return _index == _json.length(); }
    int ReadIntoBuffer( char[] buf, int start, int length ) {
        int index = start;
        while(index<length && _index<_json.length())
            buf[index++] = _json.charAt(_index++);
        return index-start;
    }

    String _json;
    int _index = 0;


    void EatWhiteSpace() {
        while( "\r\n\t ".indexOf( SafePeekChar() ) >= 0 )
            UnsafeReadChar();

        if( AtEnd() )
            throw new IllegalStateException( "Deserialize reached end of text reader without encountering any JSON objects." );
    }

    void ReadString( String str ) {
        char[] buf = new char[str.length()];
        ReadIntoBuffer( buf, 0, str.length() );
        if( str != new String( buf ) )
            throw new IllegalStateException( "didn't read expected string: " + str );
    }

    String ReadString( int length ) {
        char[] buf = new char[length];
        int bytesRead = ReadIntoBuffer( buf, 0, length );
        if( bytesRead != length )
            throw new IllegalStateException( String.format( "Only read {0} when expecting {1}.", bytesRead, length ) );
        return new String( buf );
    }

    // checks if there is a null and reads it in.
    boolean ReadNullIfThere() {
        if( SafePeekChar() != 'n' ) return false;
        ReadString( "null" );
        return true;
    }

    char ReadAnyCharOrThrowExceptionIfAtEnd() {
        if( AtEnd() ) throw new IllegalStateException( "reached end of stream/string unexpectedly." );
        return UnsafeReadChar();
    }

    void ReadSpecificChar( char k ) {
        EatWhiteSpace();
        char readChar = ReadAnyCharOrThrowExceptionIfAtEnd();
        if( k != readChar )
            throw new IllegalStateException( "Expected "+k+" but found "+readChar );
    }

    boolean TryReadChar( char k ) {
        EatWhiteSpace();
        if( SafePeekChar() != k ) return false;
        UnsafeReadChar();
        return true;
    }

}
