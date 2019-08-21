package ${basePackage}.controller;

import ${basePackage}.core.Result;
import ${basePackage}.core.ResultGenerator;
import ${basePackage}.entity.${modelNameUpperCamel};
import ${basePackage}.service.${modelNameUpperCamel}Service;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.java.Log;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
* Created by ${author} on ${date}.
*/
@RestController
@RequestMapping("${baseRequestMapping}")
@Log
public class ${modelNameUpperCamel}Controller {
    @Resource
    private ${modelNameUpperCamel}Service ${modelNameLowerCamel}Service;

    @ApiOperation(value="增加${modelNameLowerCamel}", notes="自动生成")
    @ApiImplicitParam(name = "${modelNameLowerCamel}", value = "${modelNameLowerCamel}", required = true, dataType = "${modelNameUpperCamel}")
    @PostMapping
    public Result add(@RequestBody ${modelNameUpperCamel} ${modelNameLowerCamel}) {
        ${modelNameLowerCamel}Service.save(${modelNameLowerCamel});
        return ResultGenerator.genSuccessResult();
    }

    @ApiOperation(value="根据id删除${modelNameLowerCamel}", notes="自动生成")
    @ApiImplicitParam(name = "id", value = "${modelNameLowerCamel}Id", required = true, dataType = "string")
    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Integer id) {
        ${modelNameLowerCamel}Service.deleteById(id);
        return ResultGenerator.genSuccessResult();
    }

    @ApiOperation(value="根据id更新${modelNameLowerCamel}", notes="自动生成")
    @ApiImplicitParam(name = "${modelNameLowerCamel}", value = "${modelNameLowerCamel}", required = true, dataType = "${modelNameUpperCamel}")
    @PutMapping
    public Result update(@RequestBody ${modelNameUpperCamel} ${modelNameLowerCamel}) {
        ${modelNameLowerCamel}Service.update(${modelNameLowerCamel});
        return ResultGenerator.genSuccessResult();
    }

    @ApiOperation(value="根据id获取${modelNameLowerCamel}详情", notes="自动生成")
    @ApiImplicitParam(name = "id", value = "${modelNameLowerCamel}Id", required = true, dataType = "string")
    @GetMapping("/{id}")
    public Result detail(@PathVariable Integer id) {
        ${modelNameUpperCamel} ${modelNameLowerCamel} = ${modelNameLowerCamel}Service.findById(id);
        return ResultGenerator.genSuccessResult(${modelNameLowerCamel});
    }

    @ApiOperation(value="根据页码及单页数据条数获取${modelNameLowerCamel}列表", notes="自动生成")
    @ApiImplicitParams({
        @ApiImplicitParam(dataType = "int", name = "pageIndex", value = "页码", required = true),
        @ApiImplicitParam(dataType = "int", name = "pageSize", value = "数据条数", required = true)
    })
    @GetMapping
    public Result list(@RequestParam(defaultValue = "0") Integer pageIndex, @RequestParam(defaultValue = "10") Integer pageSize) {
        PageHelper.startPage(pageIndex, pageSize);
        List<${modelNameUpperCamel}> list = ${modelNameLowerCamel}Service.findAll();
        PageInfo pageInfo = new PageInfo(list);
        return ResultGenerator.genSuccessResult(pageInfo);
    }
}
