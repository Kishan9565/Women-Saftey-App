package com.example.raksha.repository

import com.example.raksha.data.Category
import com.example.raksha.data.SafetyTip

object SafetyTipsRepository {

    val categories = listOf(
        Category("General", true),
        Category("Travel"),
        Category("Home"),
        Category("Online"),
        Category("Emergency")
    )

    val safetyTips = listOf(
        // 1-10: General Personal Safety
        SafetyTip(1, "General", "Stay Aware of Surroundings",
            "Keep your head up and scan people and exits. Avoid deep phone/headphone distraction when walking alone."),
        SafetyTip(2, "General", "Share Your Plan",
            "Tell one trusted person where you'll go and expected arrival time; share live location if possible."),
        SafetyTip(3, "General", "Keep Phone Charged & Power Bank",
            "Ensure phone has enough battery before going out; carry a small power bank for emergencies."),
        SafetyTip(4, "General", "Use Well-Lit Routes",
            "Prefer main roads and well-lit routes at night; avoid deserted shortcuts and isolated alleys."),
        SafetyTip(5, "General", "Trust Your Instincts",
            "If a situation or person feels wrong, remove yourself and seek a public place or help."),
        SafetyTip(6, "General", "Carry a Safety Whistle",
            "A loud whistle attracts attention quickly—carry one in your bag or on keys."),
        SafetyTip(7, "General", "Memorize Emergency Numbers",
            "Keep local emergency numbers (112, 1091, local police) saved and easy to access."),
        SafetyTip(8, "General", "Keep Important Contacts on Speed Dial",
            "Set 1–3 trusted contacts to emergency speed dial or widget for one-tap calling."),
        SafetyTip(9, "General", "Avoid Showing Valuables",
            "Don't flash expensive phones, jewelry, or cash in public—keep them discreet."),
        SafetyTip(10, "General", "Learn Basic Self-Defense Moves",
            "Enroll in short self-defence classes; even one or two techniques (escape grips) help in panic situations."),

        // 11-20: Travel & Street Safety
        SafetyTip(11, "Travel", "Confirm Vehicle Before Boarding",
            "Before getting into a cab/auto, check vehicle number, driver photo, and confirm via app/ride details."),
        SafetyTip(12, "Travel", "Share Trip Live",
            "Share live trip tracking with a trusted contact until you reach safely."),
        SafetyTip(13, "Travel", "Sit Where You Can Exit",
            "In taxis/autorickshaws, sit where you can easily step out (rear seat center in cars may be safe in groups)."),
        SafetyTip(14, "Travel", "Note Landmarks",
            "If you feel unsafe, note nearby landmarks or shops to ask for help quickly."),
        SafetyTip(15, "Travel", "Avoid Cash-Heavy Stops Alone",
            "When withdrawing cash or using ATMs, prefer daytime and visible ATM booths or bank counters."),
        SafetyTip(16, "Travel", "Use Trusted Transport Services",
            "Prefer verified taxi apps or registered services over unknown phone-booked taxis."),
        SafetyTip(17, "Travel", "Walk Facing Traffic When No Sidewalk",
            "If forced onto the road, walk facing traffic so you can see cars and avoid surprises."),
        SafetyTip(18, "Travel", "Plan Your Route in Advance",
            "Know approximate route and expected duration before leaving to avoid confusion en route."),
        SafetyTip(19, "Travel", "Keep Copies of IDs",
            "Keep a photocopy or photo of important ID separate from the original in case lost/robbed."),
        SafetyTip(20, "Travel", "Use Grouping at Night",
            "When possible, travel with friends or stay where other people are if feeling uneasy at night."),

        // 21-30: Home & Accommodation Safety
        SafetyTip(21, "Home", "Secure Home Entry Points",
            "Lock doors and windows; use door chains and peepholes before opening to unknown visitors."),
        SafetyTip(22, "Home", "Don’t Open Doors Blindly",
            "Ask for identification through the door or use intercoms; call building security if unsure."),
        SafetyTip(23, "Home", "Share Minimal Details with Strangers",
            "Avoid telling strangers your exact home address or personal schedules."),
        SafetyTip(24, "Home", "Emergency Escape Plan",
            "Have a simple escape plan and practice at least mentally: exits, neighbor contact, assembly point."),
        SafetyTip(25, "Home", "Keep Trusted Neighbors List",
            "Save numbers of trustworthy neighbors or building staff you can call in a hurry."),
        SafetyTip(26, "Home", "Use Timers for Lights When Away",
            "If traveling, timers for lights create the impression someone is home and lower risk."),
        SafetyTip(27, "Home", "Discreet Deliveries",
            "For online deliveries, avoid posting exact delivery times publicly and request safe drop locations."),
        SafetyTip(28, "Home", "Secure Keys & Spare Keys",
            "Don’t leave spare keys outside hidden; give to a trusted person instead."),
        SafetyTip(29, "Home", "Keep a Small First Aid Kit",
            "Have a compact first-aid kit and basic medicines stored in an accessible place."),
        SafetyTip(30, "Home", "Enable Door/Window Alarms",
            "Simple door sensors or alarms can give early warning for forced entry."),

        // 31-40: Digital & Online Safety
        SafetyTip(31, "Online", "Limit Personal Info on Social Media",
            "Avoid sharing home address, travel plans, or personal schedules publicly."),
        SafetyTip(32, "Online", "Tighten Privacy Settings",
            "Set social media privacy so only trusted contacts see posts and tagged photos."),
        SafetyTip(33, "Online", "Use Strong Unique Passwords",
            "Use unique passwords per site and enable two-factor authentication where possible."),
        SafetyTip(34, "Online", "Be Careful with Location Tags",
            "Don’t tag exact location (home/hotel) in public posts until after you leave."),
        SafetyTip(35, "Online", "Beware of Scams & Blackmail",
            "If contacted by strangers with demands, do not share images or pay—report to platform & authorities."),
        SafetyTip(36, "Online", "Keep Apps & OS Updated",
            "Regular updates patch security holes; enable automatic updates where possible."),
        SafetyTip(37, "Online", "Review App Permissions",
            "Only give apps permissions they actually need (camera, mic, location). Revoke unused ones."),
        SafetyTip(38, "Online", "Use Official Cyber Helplines",
            "For online abuse, report to platform and national cybercrime helpline; save evidence/screenshots."),
        SafetyTip(39, "Online", "Avoid Public Wi-Fi for Sensitive Actions",
            "Don’t log in to banking or confidential apps over open public Wi-Fi; use mobile data or VPN."),
        SafetyTip(40, "Online", "Educate About Deepfakes & Image Misuse",
            "Be cautious about images—report impersonation and misuse; keep originals private."),

        // 41-50: Emergency Handling & Practical Tools
        SafetyTip(41, "Emergency", "Set Quick SOS on Lock Screen",
            "Enable emergency info and quick call (ICE) features on your phone lock screen for first responders."),
        SafetyTip(42, "Emergency", "One-Tap Emergency Contacts",
            "Set one or two trusted emergency contacts in phone settings for fast access."),
        SafetyTip(43, "Emergency", "Use Loud Noise to Attract Help",
            "Yelling specific phrases (e.g., 'Fire!' or 'Call police!') often attracts faster help than 'help'."),
        SafetyTip(44, "Emergency", "Keep a Simple Escape Route",
            "When entering unfamiliar places, note exits and escape paths mentally for quick exit."),
        SafetyTip(45, "Emergency", "Record & Preserve Evidence",
            "If safe to do so, record incidents (audio/video) or note details; preserve screenshots and time stamps."),
        SafetyTip(46, "Emergency", "Report Immediately",
            "Report harassment/assault to local police and/or helplines; early reporting helps faster action."),
        SafetyTip(47, "Emergency", "Know Where to Go for Help",
            "Identify local safe places (police station, hospital, 24×7 shops) in the neighborhood ahead of time."),
        SafetyTip(48, "Emergency", "Carry Pepper Spray / Legal Self-Defense Tools",
            "If legal in your area, carry pepper spray and learn its safe use and laws around it."),
        SafetyTip(49, "Emergency", "Teach Children & Friends Basic Safety",
            "Share simple safety rules (who to call, where to go) with family and younger friends."),
        SafetyTip(50, "Emergency", "Counseling & Aftercare",
            "After any traumatic incident, seek medical care and counselling; contact NGOs/helplines for support.")
    )


    fun getTipsByCategory(category: String): List<SafetyTip> {
        return safetyTips.filter { it.category == category }
    }
}
