package de.scraper.kitaverzeichnis.service;

import de.scraper.kitaverzeichnis.model.*;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.NumberUtils;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class KitaVerzeichnisPageInterpreter {
    private final Pattern PLZ_PATTERN = Pattern.compile("\\d{5}");
    private final Pattern BEZIRK_PATTERN = Pattern.compile("\\(.*\\)");

    private static final Logger LOG = LoggerFactory.getLogger(KitaVerzeichnisPageInterpreter.class);

    public Kita interpretPage(final Document page) {
        return Kita.builder()
                .name(replaceAnfuehrungszeichen(page.select("#lblKitaname").text()))
                .art(page.select("#lblEinrichtungsart").text())
                .telefon(page.select("#lblTelefon").text())
                .email(page.select("#HLinkEMail").text())
                .link(page.select("#HLinkWeb").text())
                .thematischeSchwerpunkt(page.select("#lblPaedSchwerpunkte").text())
                .paedagogischeAnsaetze(page.select("#lblPaedAnsaetze").text())
                .thematischeSchwerpunkt(page.select("#lblThemSchwerpunkte").text())
                .mehrsprachigkeit(page.select("#lblMehrsprachigkeit").text())
                .traeger(Traeger.builder()
                        .name(page.select("#lblTraegerName").text())
                        .art(replaceKlammern(page.select("#lblTraegerart").text()))
                        .build())
                .adresse(createAdresseFromPage(page))
                .montag(createOeffnungszeit(page.select("#lblOeffnungMontag").first()))
                .dienstag(createOeffnungszeit(page.select("#lblOeffnungDienstag").first()))
                .mittwoch(createOeffnungszeit(page.select("#lblOeffnungMittwoch").first()))
                .donnerstag(createOeffnungszeit(page.select("#lblOeffnungDonnerstag").first()))
                .freitag(createOeffnungszeit(page.select("#lblOeffnungFreitag").first()))
                .plaetze(createPlaetze(page))
                .build();
    }

    private Plaetze createPlaetze(final Document page) {
        return Plaetze.builder()
                .angeboten(getNumberFromString(page.select("#pnlPlatzstrukturen tr.odd td").get(0).text()))
                .unterDrei(getNumberFromString(page.select("#pnlPlatzstrukturen tr.odd td").get(1).text()))
                .ueberDrei(getNumberFromString(page.select("#pnlPlatzstrukturen tr.odd td").get(2).text()))
                .aufnahmealter(getNumberFromString(page.select("#pnlPlatzstrukturen tr.odd td").get(3).text()))
                .build();
    }

    private int getNumberFromString(final String text) {
        return StringUtils.isBlank(text) ? 0 : NumberUtils.parseNumber(text, Integer.class);
    }

    private Oeffnungszeit createOeffnungszeit(final Element element) {
        if (element != null) {
            final String tag = element.select("span b").text();
            final String[] vonBis = element.select("span").text()
                    .replace(tag, "").split("-");

            return Oeffnungszeit.builder()
                    .tag(dayOfWeekFromString(tag))
                    .von(LocalTime.parse(vonBis[0].trim() + ":00"))
                    .bis(LocalTime.parse(vonBis[1].trim() + ":00"))
                    .build();
        }
        return null;
    }

    private DayOfWeek dayOfWeekFromString(final String tag) {
        switch (tag) {
            case "Mo":
                return DayOfWeek.MONDAY;
            case "Di":
                return DayOfWeek.TUESDAY;
            case "Mi":
                return DayOfWeek.WEDNESDAY;
            case "Do":
                return DayOfWeek.THURSDAY;
            case "Fr":
                return DayOfWeek.FRIDAY;
            default:
                return null;
        }
    }

    private String replaceKlammern(final String text) {
        return text.replaceAll("[\\(\\)]", "");
    }

    private String replaceAnfuehrungszeichen(final String text) {
        return text.replaceAll("[\\(\\)]", "");
    }

    private Adresse createAdresseFromPage(final Document page) {
        final String strasseHausnummer = page.select("#lblStrasse").text();
        final String ortPlzBezirk = page.select("#lblOrt").text();
        final Matcher plzMatcher = PLZ_PATTERN.matcher(ortPlzBezirk);
        final Matcher bezirkMatcher = BEZIRK_PATTERN.matcher(ortPlzBezirk);

        String plz = "";
        if (plzMatcher.find()) {
            plz = plzMatcher.group(0);
        }
        String bezirk = "";
        if (bezirkMatcher.find()) {
            bezirk = bezirkMatcher.group(0);
        }

        final String ort = ortPlzBezirk.replace(plz, "").replace(bezirk, "").trim();

        return Adresse.builder()
                .strasseHausnummer(strasseHausnummer)
                .plz(plz)
                .ort(ort)
                .bezirk(replaceKlammern(bezirk))
                .build();
    }
}
