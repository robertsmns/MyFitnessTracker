package com.schule.myfitnessTracker.util

import android.content.Context
import android.content.Intent
import androidx.core.content.FileProvider
import com.schule.myfitnessTracker.data.model.RoutePoint
import com.schule.myfitnessTracker.data.model.Run
import java.io.File
import java.text.SimpleDateFormat
import java.util.*

/**
 * Hilfsklasse zum Exportieren eines Runs als GPX-Datei.
 */
object GpxExporter {

    fun shareGpx(context: Context, run: Run, points: List<RoutePoint>) {
        val gpxContent = buildGpxString(run, points)
        val fileName = "Run_${SimpleDateFormat("yyyyMMdd_HHmm", Locale.getDefault()).format(Date(run.startTime))}.gpx"
        
        try {
            val file = File(context.cacheDir, fileName)
            file.writeText(gpxContent)

            val uri = FileProvider.getUriForFile(
                context,
                "${context.packageName}.fileprovider",
                file
            )

            val typeLabel = when (run.activityType) {
                "WALKING" -> "Gehen"
                "RUNNING" -> "Laufen"
                "BICYCLE" -> "Radfahren"
                "VEHICLE" -> "Fahrt"
                else      -> "Aktivität"
            }

            val intent = Intent(Intent.ACTION_SEND).apply {
                type = "application/gpx+xml"
                putExtra(Intent.EXTRA_STREAM, uri)
                putExtra(Intent.EXTRA_SUBJECT, "Mein/e $typeLabel vom ${SimpleDateFormat("dd.MM.yyyy", Locale.getDefault()).format(Date(run.startTime))}")
                addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
            }
            context.startActivity(Intent.createChooser(intent, "GPX Exportieren"))
            
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun buildGpxString(run: Run, points: List<RoutePoint>): String {
        val isoFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.US).apply {
            timeZone = TimeZone.getTimeZone("UTC")
        }
        
        val sb = StringBuilder()
        sb.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n")
        sb.append("<gpx version=\"1.1\" creator=\"MyFitnessTracker\" xmlns=\"http://www.topografix.com/GPX/1/1\">\n")
        sb.append("  <metadata>\n")
        sb.append("    <time>${isoFormat.format(Date(run.startTime))}</time>\n")
        sb.append("  </metadata>\n")
        sb.append("  <trk>\n")
        sb.append("    <name>Lauf am ${SimpleDateFormat("dd.MM.yyyy", Locale.getDefault()).format(Date(run.startTime))}</name>\n")
        sb.append("    <trkseg>\n")
        
        for (p in points) {
            sb.append("      <trkpt lat=\"${p.latitude}\" lon=\"${p.longitude}\">\n")
            sb.append("        <ele>${p.altitude}</ele>\n")
            sb.append("        <time>${isoFormat.format(Date(p.timestamp))}</time>\n")
            sb.append("      </trkpt>\n")
        }
        
        sb.append("    </trkseg>\n")
        sb.append("  </trk>\n")
        sb.append("</gpx>")
        
        return sb.toString()
    }
}
