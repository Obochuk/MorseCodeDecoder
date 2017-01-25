public class MorseCodeDecoder {
    public static String decodeBits(String bits) {
        String filtred  = bits.substring(bits.indexOf("1"), bits.lastIndexOf("1") + 1);
        int time = getTimeOfSignal(filtred);
        StringBuilder result = new StringBuilder();
        for (String word: filtred.split(repeatString("0", time * 7))){
            for (String character: word.split(repeatString("0", time * 3))){
                for (String sym: character.split(repeatString("0", time))){
                    if (sym.length() == 3 * time)
                        result.append('-');
                    else if(sym.length() == time)
                        result.append('.');
                }
                result.append(" ");
            }
            result.append("  ");
        }
        return new String(result).trim();
    }

    public static String decodeMorse(String morseCode) {
        StringBuilder result = new StringBuilder();
        for (String word : morseCode.trim().split("   ")) {
            for (String letter : word.split("\\s+"))
                result.append(MorseCode.get(letter));
            result.append(" ");
        }
        result.deleteCharAt(result.length() - 1);
        return new String(result);
    }

    private static String repeatString(String str, int times){
        return new String(new char[times]).replaceAll("\0", str);
    }
    private static int getTimeOfSignal(String bits){                // require String without extra 0`s
        if (bits.indexOf("0") < 0)
            return bits.length();
        char nextValue = '0';
        int minLen, maxLen, length, index;
        /*strange, difficult but I don`t know how to make it easier.
        * All you need to know - we only need two different lengths of
         * signal to determine time unit*/        // bike of mine
        for (maxLen = minLen = length = index= bits.indexOf(nextValue); // initialization
             minLen == maxLen && length > 0;                            // expression
             minLen = Math.min(minLen, length), maxLen = Math.max(maxLen, length), //  modifications
                     length = bits.indexOf(nextValue, index) - index, index += length) {
            nextValue = (nextValue == '0' ? '1' : '0');                 // body
        }

        if (maxLen / 7 == minLen || maxLen / 3 == minLen)
            return minLen;
        else if ((maxLen / 7) * 3 == minLen)
            return minLen / 3;
        return minLen;
    }
}