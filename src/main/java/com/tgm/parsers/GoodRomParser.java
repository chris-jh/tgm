/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tgm.parsers;

import com.tgm.data.rom.goodgen.GoodRom;
import com.tgm.utils.RegExpUtils;

/**
 *
 * @author christopher
 */
public class GoodRomParser {

//               ..................
//...............: STANDARD CODES ::...............
//:                                               :\
//:   [a?] Alternate       [p?] Pirate            :\
//:   [b?] Bad Dump        [t?] Trained           :\
//:   [f?] Fixed           [T-] OldTranslation    :\
//:   [o?] Overdump        [T+] NewerTranslation  :\
//:   [h?] Hack            (-) Unknown Year       :\
//:   [!p] Pending Dump    [!] Verified Good Dump :\
//:  (M#) Multilanguage (# of Languages)          :\
//: (###) Checksum       (??k) ROM Size           :\
//:                      (Unl) Unlicensed	  :\
//:...............................................:\
// \\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\
//
//                .................
//................: COUNTRY CODES ::...............
//:                                               :\
//:   (1) Japan & Korea      (4) USA & BrazilNTSC :\
//:   (A) Australia          (J) Japan            :\
//:   (B) non USA (Genesis)  (K) Korea            :\
//:   (C) China             (NL) Netherlands      :\
//:   (E) Europe            (PD) Public Domain    :\
//:   (F) France             (S) Spain            :\
//:  (FC) French Canadian   (Sw) Sweden           :\
//:  (FN) Finland            (U) USA              :\
//:   (G) Germany           (UK) England          :\
//:  (GR) Greece           (Unk) Unknown Country  :\
//:  (HK) Hong Kong          (I) Italy		:\
//:  (D)  Dutch            (Unl) Unlicensed       :\
//:...............................................:\
// \\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\
//
//    
    private final static String AP = "\\[a.\\]";
    private final static String PP = "\\[p.\\]";
    private final static String BP = "\\[b.\\]";
    private final static String TP = "\\[t.\\]";
    private final static String FP = "\\[f.\\]";
    private final static String OTP = "\\[T\\-\\]";
    private final static String OP = "\\[o.\\]";
    private final static String NTP = "\\[T\\+\\]";
    private final static String HP = "\\[h.\\]";
    private final static String PDP = "\\[\\!p\\]";
    private final static String GDP = "\\[\\!\\]";
    private final static String CSP = "\\(([0-9]+)\\)";
    private final static String MLP = "\\(M.*\\)";
    private final static String RSP = "\\(([0-9]+)k\\)";
    private final static String UYP = "\\(-\\)";
    private final static String CP[] = {" \\(.*[1ABCEFGD4JKSUIW]\\)", " \\(FC\\)",
        " \\(FN\\)", " \\(GR\\)", " \\(HK\\)", " \\(NL\\)", " \\(PD\\)", " \\(Sw\\)", " \\(UK\\)", " \\(Unk\\)", "\\(Unl\\)"};

    public static GoodRom parse(GoodRom rom) {
        String r = rom.getFileName();

        rom.setName(r.substring(0, r.indexOf("(")).trim());
        rom.setAlternative(RegExpUtils.substringRegExp(r, AP));
        rom.setBadDump(RegExpUtils.substringRegExp(r, BP));
        rom.setChecksum(RegExpUtils.substringRegExp(r, CSP));
        rom.setFixed(RegExpUtils.substringRegExp(r, FP));
        rom.setHack(RegExpUtils.substringRegExp(r, HP));
        rom.setMultiLanguage(RegExpUtils.substringRegExp(r, MLP));
        rom.setNewTranslation(RegExpUtils.substringRegExp(r, NTP));
        rom.setOldTranslation(RegExpUtils.substringRegExp(r, OTP));
        rom.setOverdump(RegExpUtils.substringRegExp(r, OP));
        rom.setPendingDump(RegExpUtils.match(r, PDP));
        rom.setPirate(RegExpUtils.substringRegExp(r, PP));
        rom.setRomSize(RegExpUtils.substringRegExp(r, RSP));
        rom.setTrained(RegExpUtils.substringRegExp(r, TP));
        rom.setUnknownYear(RegExpUtils.match(r, UYP));
        rom.setVerifiedGoodDump(RegExpUtils.match(r, GDP));
        rom.setRegion(RegExpUtils.substringMultiRegExp(r, CP));
        //rom.setVersion(r);
        return rom;
    }
}
