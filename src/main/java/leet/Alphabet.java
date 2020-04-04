package leet;

import java.util.Arrays;
import java.util.List;

/**
 * https://de.wikipedia.org/wiki/Leetspeak
 */
public enum Alphabet {
    A("4", "@", "/\\", "/-\\", "?", "^", "α", "λ"),
    B("8", "|3", "ß", "l³", "13", "I3", "J3"),
    C("(", "[", "<", "©", "¢"),
    D("|)", "|]", "Ð", "đ", "1)"),
    E("3", "€", "&", "£", "ε"),
    F("|=", "PH", "|*|-|", "|\\\"", "ƒ", "l²"),
    G("6", "&", "9"),
    H("#", "4", "|-|", "}{", "]-[", "/-/", ")-("),
    I("!", "1", "|", "][", "ỉ"),
    J("_|", "¿"),
    K("|<", "|{", "|(", "X"),
    L("1", "|_", "£", "|", "][_"),
    M("/\\/\\", "/v\\", "|V|", "]V[", "|\\/|", "AA", "[]V[]", "|11", "/|\\", "^^", "(V)", "|Y|", "!\\/!"),
    N("|\\|", "/\\/", "/V", "|V", "/\\\\/", "|1", "2", "?", "(\\)", "11", "r", "!\\!"),
    O("0", "9", "()", "[]", "*", "°", "<>", "ø", "{[]}"),
    P("9", "|°", "p", "|>", "|*", "[]D", "][D", "|²", "|?", "|D"),
    Q("0_", "0,"),
    R("2", "|2", "1²", "®", "?", "я", "12", ".-"),
    S("5", "$", "§", "?", "ŝ", "ş"),
    T("7", "+", "†", "']['", "|"),
    U("|_|", "µ", "[_]", "v"),
    V("\\/", "|/", "\\|", "\\'"),
    W("\\/\\/", "VV", "\\A/", "\\V/", "\\\\'", "uu", "\\^/", "\\|/", "uJ"),
    X("><", ")(", "}{", "%", "?", "×", "]["),
    Y("`/", "°/", "¥"),
    Z("z", "2", "\\\"/_"),
    Ä("43", "°A°", "°4°"),
    Ö("03", "°O°"),
    Ü("|_|3", "°U°");
    
    public List<String> translations;

    private Alphabet(String... translations) {
        this.translations = Arrays.asList(translations);
    }
}