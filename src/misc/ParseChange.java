package misc;
public class ParseChange {
    public static String parse(String toTranslate){
        switch(toTranslate){
            case "ENTRY_MODIFY":
                return "modified";

            case "ENTRY_CREATE":
                return "created";

            case "ENTRY_DELETE":
                return "DELETED";

            default:
                return toTranslate;
        }
    }
}
