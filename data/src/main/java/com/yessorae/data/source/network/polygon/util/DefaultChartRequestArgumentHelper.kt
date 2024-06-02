package com.yessorae.data.source.network.polygon.util

import com.yessorae.domain.common.ChartRequestArgumentHelper
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import javax.inject.Inject

class DefaultChartRequestArgumentHelper @Inject constructor() : ChartRequestArgumentHelper {
    // The full set should be 500 tickers
    private val tickers: Set<String> by lazy {
        setOf(
            "AAPL", "MSFT", "GOOGL", "AMZN", "META", "BRK.B", "JNJ", "V", "PG", "NVDA",
            "JPM", "UNH", "HD", "DIS", "MA", "PYPL", "NFLX", "ADBE", "INTC", "CMCSA",
            "XOM", "KO", "PFE", "CSCO", "T", "PEP", "ABBV", "MRK", "AVGO", "WMT",
            "MCD", "CVX", "CRM", "ABT", "ACN", "MDT", "QCOM", "NEE", "LLY", "NKE",
            "TXN", "PM", "ORCL", "BA", "IBM", "HON", "COST", "WFC", "UNP", "AMGN",
            "DHR", "LIN", "MMM", "LOW", "SPGI", "CAT", "TMO", "AXP", "GS", "MS",
            "GE", "LMT", "NOW", "BLK", "RTX", "ELV", "ISRG", "BKNG", "ADP", "GILD",
            "SCHW", "MDLZ", "INTU", "FIS", "CHTR", "CB", "SYK", "ZTS", "PLD", "LRCX",
            "DUK", "CI", "APD", "MU", "SO", "MMC", "CME", "CCI", "ICE", "ECL",
            "NSC", "GM", "SBUX", "ETN", "D", "ITW", "DE", "EW", "BDX", "MCO",
            "SPG", "BSX", "TEL", "ROP", "TRV", "AON", "MET", "USB", "PNC", "WM",
            "AEP", "TGT", "ADSK", "NOC", "NSC", "FDX", "CL", "MNST", "KDP", "EOG",
            "F", "C", "VRTX", "CSX", "AFL", "PSA", "ADI", "MRNA", "IDXX", "IQV",
            "CTAS", "KLAC", "APH", "NXPI", "EXC", "KHC", "APH", "STZ", "DG", "JCI",
            "ALL", "ILMN", "SRE", "ADSK", "ROP", "CDNS", "MCHP", "ORLY", "ANET", "TT",
            "OXY", "PXD", "DVN", "FANG", "CTRA", "FTNT", "TSLA", "PAYC", "CTLT", "AVB",
            "DLR", "ARE", "O", "SBAC", "AMT", "CCI", "EXR", "PSA", "RMD", "CSGP",
            "PPL", "PEG", "ED", "XEL", "ES", "WEC", "EIX", "AEE", "LNT", "AEP",
            "FE", "NI", "CMS", "DTE", "ETR", "EVRG", "PNW", "SO", "D", "EXC",
            "MHK", "BWA", "LEG", "FBHS", "MAS", "PH", "ROK", "SWK", "EMR", "ETN",
            "HWM", "JCI", "LII", "MMM", "IR", "CMI", "DOV", "FLS", "GWW", "XYL",
            "AOS", "BKR", "BA", "LMT", "GD", "NOC", "RTX", "HII", "TDG", "TXT",
            "LHX", "AME", "HON", "RSG", "WM", "NUE", "STLD", "X", "AKS", "MT",
            "FCX", "AA", "CENX", "STLD", "ATI", "SLGN", "PKG", "IP", "WRK", "SEE",
            "SON", "AVY", "BMS", "NLSN", "GPC", "AAP", "AZO", "ORLY", "SIG", "TIF",
            "BBY", "BBWI", "KSS", "M", "JWN", "RL", "GPS", "ANF", "URBN", "LB",
            "TGT", "WMT", "COST", "DG", "DLTR", "BBBY", "CVS", "WBA", "RAD", "GME",
            "PLCE", "EXPE", "BKNG", "CCL", "RCL", "NCLH", "MAR", "HLT", "H", "WH",
            "PK", "APLE", "DRH", "HST", "XOM", "CVX", "COP", "OXY", "PXD", "MPC",
            "VLO", "PSX", "HES", "MRO", "HFC", "DVN", "APA", "CLR", "FANG", "CXO",
            "EOG", "PBF", "HFC", "XEC", "MUR", "WES", "OKE", "KMI", "WMB", "EPD",
            "ENB", "ET", "PAGP", "TRGP", "MPLX", "ONEOK", "CEQP", "MMP", "AMLP", "GEL",
            "GLOP", "HMLP", "KNOP", "CQP", "USAC", "NS", "NGL", "DCP", "PAA", "SUN",
            "HEP", "BPMP", "SHLX", "ENBL", "CPG", "ERF", "CVE", "CNQ", "IMO", "SU",
            "AR", "COG", "CHK", "EQT", "MTDR", "RRC", "SWN", "APA", "DVN", "MRO",
            "CVE", "XOM", "PXD", "APA", "FANG", "CLR", "MTDR", "PAA", "MMP", "ENB",
            "ET", "BPMP", "AMLP", "EPD", "GEL", "GLOP", "HMLP", "KNOP", "CQP", "USAC",
            "NS", "NGL", "DCP", "PAA", "SUN", "HEP", "BPMP", "SHLX", "ENBL", "CEQP"
        )
    }

    override fun getRandomTicker(): String {
        return tickers.random()
    }

    override fun getFromDate(): String {
        return LocalDate.now()
            .minusDays(1)
            .minusYears(1)
            .minusMonths(6)
            .convertToString()
    }

    override fun getToDate(): String {
        return LocalDate.now()
            .minusDays(1)
            .convertToString()
    }

    private fun LocalDate.convertToString(): String {
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
        return this.format(formatter)
    }
}
