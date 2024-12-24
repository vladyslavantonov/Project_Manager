package ua.opu.projectmanager.models

data class Project(
    var name: String,
    var description: String,
    var deadline: String,
    var participants: String,
    var category: String,
    var isCompleted: Boolean = false // Стан виконання проекту
)
