package com.company;



import antlr.collections.Stack;
import generation.Compiler;
import generation.Generator;
import generation.*;
import grammar.*;
import org.antlr.runtime.ANTLRFileStream;
import org.antlr.runtime.CommonTokenStream;
import org.antlr.runtime.RecognitionException;
import org.antlr.runtime.tree.Tree;
import TDS.*;

//import TDS.*;

import java.io.*;

public class Main {
    public static void main(String[] args) throws IOException,  RecognitionException {
        String source = "src/com/company/Recursif3.algol";
        String outSt = "out/artifacts/compile_jar/tds.txt";
        String out = "out/artifacts/compile_jar/gen1.txt";

        AlgolLexer lex = new AlgolLexer(new ANTLRFileStream(source, "UTF8"));
        CommonTokenStream tokens = new CommonTokenStream(lex);
        AlgolParser g = new AlgolParser(tokens);

        try {
            AlgolParser.prog_return ret = g.prog();
            Tree root = (Tree)ret.getTree();
            TDS.TreeTraversal treeTraversal = new TDS.TreeTraversal(root);
           //Stack t= (Stack) treeTraversal.gestionnaireTDS.getStack();

            TDS.tableDesSymboles symbolTable = treeTraversal.buildSymbolTable(null,"Root");


            if(!outSt.equals("")) {
               /// System.out.println(symbolTable.toTable());
               BufferedWriter writer = new BufferedWriter(new FileWriter(outSt));
                writer.write(symbolTable.toTable().toString());
              // System.out.println(symbolTable.getBloc());
               writer.close();
            }

            File sourceFile = new File(source);
            File genFile = new File(out);
           // System.out.println("hh"+symbolTable.blocs);
            Generator generator = new Generator(sourceFile, genFile, symbolTable);
            generator.generate(root);
        //    System.out.println("hh"+symbolTable.getBloc(0).getName());
            Compiler compiler = new Compiler(genFile);
            compiler.compile();
        } catch (RecognitionException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}
