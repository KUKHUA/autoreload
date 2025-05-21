package misc;
public class ParseChange {
    public static String parse(String toTranslate){
        switch(toTranslate){
            case "ENTRY_MODIFY":
                return "modified";

            case "ENTRY_CREATE":
                return "created";

            case "ENTRY_DELETE":
                return "deleted";

            default:
                return toTranslate;
        }
    }

        public static String parseion(String toTranslate){
        switch(toTranslate){
            case "ENTRY_MODIFY":
                return "modification";

            case "ENTRY_CREATE":
                return "creation";

            case "ENTRY_DELETE":
                return "deletion";
                
            default:
                return toTranslate;
        }
    }
}
