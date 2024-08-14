package io.github.spencerpark.ijava.magics;


import io.github.spencerpark.ijava.IJava;
import io.github.spencerpark.jupyter.kernel.magic.registry.CellMagic;

import java.util.List;
import java.util.stream.Collectors;

public class ContextBuilder {

    @CellMagic(aliases = {"python"})
    public void buildPythonContext(List<String> args, String body) throws Exception {
        String code = "try {";
        code += args.stream().map(arg -> String.format("context.getBindings(\"python\").putMember(\"%s\", %s);", arg, arg)).collect(Collectors.joining());
        code += "context.eval(Source.create(\"python\", \"" + body.lines().map(line -> line.replace("\"", "\\\"")).collect(Collectors.joining("\\n")) + "\"));";
        code += "} catch (PolyglotException e) {}";
        IJava.getKernelInstance().eval(code);
    }
}
