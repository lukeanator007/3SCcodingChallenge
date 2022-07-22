package com.example.a3sccodechallenge;

public  class JsonHelper
{
    public static String[] parseJson(String[] names, String json)
    {
        String[] ans = new String[names.length];

        for(int i=0;i<names.length;i++)
        {
            int startNameIndex = json.indexOf("\"" + names[i]+"\":");
            if(startNameIndex == -1) continue;

            int startValueIndex = json.indexOf(":", startNameIndex+1);

            int index = startValueIndex;
            int bracketCount = 0;
            int endValueIndex = -1;

            while(true)
            {
                char ch = json.charAt(index);
                if(ch == '{' || ch == '[') bracketCount++;
                else if(ch == '}' || ch == ']') bracketCount--;
                else if(bracketCount == 0 && ch == ',')
                {
                    endValueIndex = index;
                    break;
                }

                index++;

                if(index == json.length()-1)
                {
                    endValueIndex = index;
                    break;
                }
            }

            ans[i] = json.substring(startValueIndex+1, endValueIndex);
        }

        return ans;
    }

    public static String parseString(String str)
    {
        if(str.equals("null")) return null;
        else return str.substring(1, str.length()-1);
    }

}

















