package com.my.activiti;

import com.google.common.collect.Maps;
import org.activiti.engine.*;
import org.activiti.engine.form.FormProperty;
import org.activiti.engine.form.TaskFormData;
import org.activiti.engine.impl.form.StringFormType;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.DeploymentBuilder;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Hello world!
 */
public class App {
    public static void main(String[] args) throws ParseException {

        System.out.println("Hello World!");

        // 创建流程引擎
        ProcessEngineConfiguration pec = ProcessEngineConfiguration.createStandaloneInMemProcessEngineConfiguration();
        ProcessEngine engine = pec.buildProcessEngine();
        String name = engine.getName();
        String version = ProcessEngine.VERSION;
        System.out.println("流程引擎名称：" + name + "  流程引擎版本：" + version);

        // 部署流程定义文件
        RepositoryService repositoryService = engine.getRepositoryService();
        DeploymentBuilder deploymentBuilder = repositoryService.createDeployment();
        deploymentBuilder.addClasspathResource("seconed_approval.bpmn");
        Deployment deployment = deploymentBuilder.deploy();
        String deploymentId = deployment.getId();
        ProcessDefinition processDefinition = repositoryService.createProcessDefinitionQuery()
                .deploymentId(deploymentId)
                .singleResult();
        System.out.println("流程定义文件：" + processDefinition.getName() + "  流程定义ID：" + processDefinition.getId());

        // 启动定义流程
        RuntimeService runtimeService = engine.getRuntimeService();
        ProcessInstance processInstance = runtimeService.startProcessInstanceById(processDefinition.getId());
        System.out.println("启动流程：" + processInstance.getProcessDefinitionKey());

        // 处理流程任务
        Scanner scanner = new Scanner(System.in);
        if (processInstance != null && !processInstance.isEnded()) {

            TaskService taskService = engine.getTaskService();
            List<Task> tasks = taskService.createTaskQuery().list();
            System.out.println("待处理的任务数量：" + tasks.size());
            for (Task task : tasks) {

                System.out.println("待处理task：" + task.getName());
                FormService formService = engine.getFormService();
                TaskFormData taskFormData = formService.getTaskFormData(task.getId());
                List<FormProperty> formProperties = taskFormData.getFormProperties();
                Map<String, Object> map = Maps.newHashMap();
                for (FormProperty formProperty : formProperties) {

                    if (StringFormType.class.isInstance(formProperty.getType())) {
                        System.out.println("请输入" + formProperty.getName());
                        String line = scanner.nextLine();
                        map.put(formProperty.getId(), line);
                    } else {

                        System.out.println("请输入日期数据");
                        String date = scanner.nextLine();
                        Date time = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(date);
                        map.put(formProperty.getId(), time);
                    }
                }
                taskService.complete(task.getId(), map);
                processInstance = engine.getRuntimeService().createProcessInstanceQuery()
                        .processInstanceId(processInstance.getId()).singleResult();
            }
        }
    }
}
