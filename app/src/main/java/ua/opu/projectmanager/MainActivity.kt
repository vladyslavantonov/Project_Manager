package ua.opu.projectmanager

import android.os.Bundle
import android.widget.*
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import ua.opu.projectmanager.models.Project

class MainActivity : AppCompatActivity() {

    private lateinit var projectListView: ListView
    private lateinit var addProjectButton: Button
    private lateinit var emptyView: TextView
    private val projectList = mutableListOf<Project>()
    private lateinit var projectAdapter: ProjectAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen()
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        projectListView = findViewById(R.id.project_list)
        emptyView = findViewById(R.id.empty_view)
        addProjectButton = findViewById(R.id.add_project_button)

        projectAdapter = ProjectAdapter(
            this,
            projectList,
            onProjectCompleted = { position ->
                projectList[position].isCompleted = true
                projectAdapter.notifyDataSetChanged()
                Toast.makeText(this, "Проєкт відзначено виконаним", Toast.LENGTH_SHORT).show()
            },
            onProjectDeleted = { position ->
                projectList.removeAt(position)
                projectAdapter.notifyDataSetChanged()
                checkIfListIsEmpty()
                Toast.makeText(this, "Проєкт видалено", Toast.LENGTH_SHORT).show()
            }
        )
        projectListView.adapter = projectAdapter

        checkIfListIsEmpty()

        addProjectButton.setOnClickListener {
            showAddProjectDialog()
        }

        projectListView.setOnItemClickListener { _, _, position, _ ->
            val project = projectList[position]
            showProjectDetailsDialog(project)
        }
    }

    private fun showAddProjectDialog() {
        val dialogView = layoutInflater.inflate(R.layout.dialog_add_project, null)
        val dialog = AlertDialog.Builder(this)
            .setTitle("Додати новий проєкт")
            .setView(dialogView)
            .setPositiveButton("Додати") { _, _ ->
                val projectName = dialogView.findViewById<EditText>(R.id.edit_project_name).text.toString()
                val projectDescription = dialogView.findViewById<EditText>(R.id.edit_project_description).text.toString()
                val projectDeadline = dialogView.findViewById<EditText>(R.id.edit_project_deadline).text.toString()
                val projectParticipants = dialogView.findViewById<EditText>(R.id.edit_project_participants).text.toString()
                val projectCategory = dialogView.findViewById<Spinner>(R.id.spinner_category).selectedItem.toString()

                val newProject = Project(projectName, projectDescription, projectDeadline, projectParticipants, projectCategory)
                projectList.add(newProject)
                projectAdapter.notifyDataSetChanged()
                checkIfListIsEmpty()
            }
            .setNegativeButton("Скасувати", null)
            .create()
        dialog.show()
    }

    private fun showProjectDetailsDialog(project: Project) {
        val dialogView = layoutInflater.inflate(R.layout.dialog_project_details, null)

        val nameField = dialogView.findViewById<EditText>(R.id.edit_project_name)
        val descriptionField = dialogView.findViewById<EditText>(R.id.edit_project_description)
        val deadlineField = dialogView.findViewById<EditText>(R.id.edit_project_deadline)
        val participantsField = dialogView.findViewById<EditText>(R.id.edit_project_participants)

        nameField.setText(project.name)
        descriptionField.setText(project.description)
        deadlineField.setText(project.deadline)
        participantsField.setText(project.participants)

        AlertDialog.Builder(this)
            .setTitle("Деталі проєкту")
            .setView(dialogView)
            .setPositiveButton("Зберегти") { _, _ ->
                project.name = nameField.text.toString()
                project.description = descriptionField.text.toString()
                project.deadline = deadlineField.text.toString()
                project.participants = participantsField.text.toString()
                projectAdapter.notifyDataSetChanged()
            }
            .setNegativeButton("Закрити", null)
            .show()
    }

    private fun checkIfListIsEmpty() {
        if (projectList.isEmpty()) {
            emptyView.visibility = TextView.VISIBLE
            projectListView.visibility = ListView.GONE
        } else {
            emptyView.visibility = TextView.GONE
            projectListView.visibility = ListView.VISIBLE
        }
    }
}
