package com.jianglianein.apigateway.model.graphql

import com.jianglianein.apigateway.model.type.BoardInput
import com.jianglianein.apigateway.model.type.ProjectInput
import com.jianglianein.apigateway.model.type.TeamInput
import com.jianglianein.apigateway.model.type.UserInput

data class SelectionInput (val userInput: UserInput? = null,
                           val teamInput: TeamInput? = null,
                           val projectInput: ProjectInput? = null,
                           val boardInput: BoardInput? = null)