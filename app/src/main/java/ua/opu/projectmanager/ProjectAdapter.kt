package ua.opu.projectmanager

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import ua.opu.projectmanager.models.Project

class ProjectAdapter(
    context: Context,
    private val projects: MutableList<Project>,
    private val onProjectCompleted: (Int) -> Unit,
    private val onProjectDeleted: (Int) -> Unit
) : ArrayAdapter<Project>(context, 0, projects) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view = convertView ?: LayoutInflater.from(context).inflate(R.layout.item_project, parent, false)

        val projectName = view.findViewById<TextView>(R.id.project_name)
        val projectCategory = view.findViewById<TextView>(R.id.project_category)
        val checkIcon = view.findViewById<ImageView>(R.id.check_project)
        val deleteIcon = view.findViewById<ImageView>(R.id.delete_project)

        val project = projects[position]

        projectName.text = project.name
        projectCategory.text = project.category

        // Зміна кольору проекту, якщо виконано
        view.setBackgroundColor(
            if (project.isCompleted) context.getColor(android.R.color.holo_green_light)
            else context.getColor(android.R.color.transparent)
        )

        // Логіка позначення проекту виконаним
        checkIcon.setOnClickListener {
            AlertDialog.Builder(context)
                .setTitle("Позначити проект виконаним")
                .setMessage("Ви дійсно хочете позначити цей проект як виконаний?")
                .setPositiveButton("Так") { _, _ -> onProjectCompleted(position) }
                .setNegativeButton("Назад", null)
                .show()
        }

        // Логіка видалення проекту
        deleteIcon.setOnClickListener {
            AlertDialog.Builder(context)
                .setTitle("Видалити проект")
                .setMessage("Ви дійсно хочете видалити цей проект?")
                .setPositiveButton("Так") { _, _ -> onProjectDeleted(position) }
                .setNegativeButton("Ні", null)
                .show()
        }

        return view
    }
}
