package com.dream.controller;

import cn.hutool.json.JSONObject;
import com.dream.config.ConvertOfficeConfig;
import com.dream.config.RabbitMQConfig;
import com.dream.service.ConvertOfficeService;
import com.dream.service.RabbitMQService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Base64;

@Api(tags="根据传入的JSON参数将Office文件转换为Pdf文件")
@RestController
@RequestMapping(value = "/api")
public class ConvertOffice {

    @Autowired
    private ConvertOfficeService convertOfficeService;

    @Autowired
    private RabbitMQService rabbitMQService;

    /**
     * 接收传入的JSON数据，将源Office文件转换为Pdf文件；按照传入的设置，将文件回写到指定位置
     * @param jsonInput 输入的JSON对象
     *{
     * 	"inputType": "path",
     * 	"inputFile": "D:/1.docx",
     * 	"inputHeaders":
     *  {
     *     		"Authorization":"Bearer da3efcbf-0845-4fe3-8aba-ee040be542c0"
     *   },
     * 	"outPutFileName": "1-online",
     * 	 "outPutFileType": "ofd",
     * 	"writeBackType": "path",
     * 	"writeBack":
     *   {
     *     		"path":"D:/"
     *   },
     * 	"writeBackHeaders":
     *   {
     *     		"Authorization":"Bearer da3efcbf-0845-4fe3-8aba-ee040be542c0"
     *   },
     * 	"callBackURL": "http://10.11.12.13/callback"
     * }
     * @return JSON结果
     */
    @ApiOperation("接收传入的JSON数据，将源Office文件转换为Pdf/Ofd文件；按照传入的设置，将文件回写到指定位置")
    @PostMapping(value = "/convert")
    public JSONObject convert2Jpg(@RequestBody JSONObject jsonInput) {
        JSONObject jsonReturn = new JSONObject();

        if(!RabbitMQConfig.producer){
            jsonReturn = convertOfficeService.ConvertOffice(jsonInput);
        }else{
            jsonReturn.set("flag", "success" );
            jsonReturn.set("message", "Set Data to MQ Success" );

            rabbitMQService.setData2MQ(jsonInput);
        }

        return jsonReturn;
    }

    /**
     * 接收传入的JSON数据，将源Office文件转换为Pdf/Ofd文件，并以Base64字符串输出。
     * 本接口只能返回一个文件的转换结果的字符串。
     * @param jsonInput 输入的JSON对象
     *{
     * 	"inputType": "path",
     * 	"inputFile": "D:/1.docx",
     * 	"inputHeaders":
     *  {
     *     		"Authorization":"Bearer da3efcbf-0845-4fe3-8aba-ee040be542c0"
     *   },
     *   "outPutFileName": "1-online",
     *   "outPutFileType": "ofd"
     * }
     * @return String结果
     */
    @ApiOperation("接收传入的JSON数据，将源Office文件转换为Pdf文件，并以Base64字符串输出。本接口只能返回一个文件的转换结果的字符串。")
    @RequestMapping(value = "/convert2base64", method = RequestMethod.POST)
    public String convert2Base64(@RequestBody JSONObject jsonInput) {

        JSONObject jsonReturn = convertOfficeService.ConvertOffice(jsonInput);

        if("success".equalsIgnoreCase(jsonReturn.getStr("flag"))){
            String strPath = ConvertOfficeConfig.outPutPath;
            strPath = strPath.replaceAll("\\\\", "/");
            if(!strPath.endsWith("/")){
                strPath = strPath + "/";
            }

            String strOutPutFileName = jsonInput.getStr("outPutFileName");
            String strOutPutFileType = jsonInput.getStr("outPutFileType");

            String strFilePathName = strPath + strOutPutFileName + "." + strOutPutFileType;
            File file = new File(strFilePathName);
            if(file.exists()){
                try {
                    byte[] b = Files.readAllBytes(Paths.get(strFilePathName));
                    // 文件转换为字节后，转换后的文件即可删除（pdf/ofd没用了）。
                    file.delete();
                    return Base64.getEncoder().encodeToString(b);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return null;
    }

}
