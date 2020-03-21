package com.jianglianein.apigateway.service

import com.fasterxml.jackson.databind.ObjectMapper
import com.jianglianein.apigateway.config.microserviceproperty.RemoteServiceProperties
import com.jianglianein.apigateway.model.type.ProjectInput
import com.jianglianein.apigateway.model.type.ProjectOutput
import com.jianglianein.apigateway.model.type.ResultOutput
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpMethod
import org.springframework.stereotype.Service
import org.springframework.util.LinkedMultiValueMap

@Service
class RemoteScrumProjectService {
    @Autowired
    private lateinit var remoteServiceProperties: RemoteServiceProperties
    @Autowired
    private lateinit var objectMapper: ObjectMapper
    @Autowired
    private lateinit var httpClientService: HttpClientService

    fun selectProjectsByCreator(creator: String): MutableList<ProjectOutput> {
        val url = remoteServiceProperties.projectServiceUrl + "/scrum_project/selectByCreator"

        val params = LinkedMultiValueMap<String, Any>()
        params.add("creator", creator)
        val resp = httpClientService.client(url, HttpMethod.POST, params)
        val javaType = objectMapper.typeFactory.constructParametricType(MutableList::class.java, ProjectOutput::class.java)
        return objectMapper.readValue(resp, javaType)
    }

    fun createProject(projectInput: ProjectInput): ResultOutput {
        val url = remoteServiceProperties.projectServiceUrl + "/scrum_project/create"

        val params = LinkedMultiValueMap<String, Any>()
        params.add("projectName", projectInput.projectName)
        params.add("creator", projectInput.creator)

        if (projectInput.teamId == null){
            params.add("teamId", "None")
        }else {
            params.add("teamId", projectInput.teamId)
        }

        if (projectInput.colTitle == null){
            params.add("colTitle", arrayListOf("block", "confirm", "dev", "test", "sign off"))
        }else {
            params.add("colTitle", projectInput.colTitle)
        }

        params.add("rowTitle", projectInput.rowTitle)

        params.add("iteration", projectInput.iteration)
        val resp = httpClientService.client(url, HttpMethod.POST, params)
        return objectMapper.readValue(resp, ResultOutput::class.java)
    }
}