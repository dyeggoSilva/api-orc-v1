package com.example.orcamento.service;

import com.example.orcamento.model.Orcamento;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import org.springframework.util.StreamUtils;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;
import org.xhtmlrenderer.pdf.ITextRenderer;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Base64;

@Service
public class PdfGeneratorService {

    private final TemplateEngine templateEngine;

    public PdfGeneratorService() {
        ClassLoaderTemplateResolver resolver = new ClassLoaderTemplateResolver();
        resolver.setPrefix("templates/");
        resolver.setSuffix(".html");
        resolver.setTemplateMode(TemplateMode.HTML);
        resolver.setCharacterEncoding("UTF-8");
        resolver.setCacheable(false);

        this.templateEngine = new TemplateEngine();
        this.templateEngine.setTemplateResolver(resolver);
    }

    public byte[] gerarPdf(Orcamento orcamento) {
        String html = renderizarHtml(orcamento);
        return converterHtmlParaPdf(html);
    }

    private String renderizarHtml(Orcamento orcamento) {
        Context context = new Context();
        context.setVariable("orcamento", orcamento);
        context.setVariable("logoBase64", carregarLogoBase64());
        return templateEngine.process("orcamento-template", context);
    }

    private String carregarLogoBase64() {
        try {
            // ajuste o caminho conforme o nome real da pasta (template ou templates)
            ClassPathResource imgFile = new ClassPathResource("templates/201c0d99-03af-4758-922f-177d357cc5f4.png");
            byte[] bytes = StreamUtils.copyToByteArray(imgFile.getInputStream());
            return Base64.getEncoder().encodeToString(bytes);
        } catch (IOException e) {
            throw new RuntimeException("Erro ao carregar logo do orçamento: " + e.getMessage(), e);
        }
    }

    private byte[] converterHtmlParaPdf(String html) {
        try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
            ITextRenderer renderer = new ITextRenderer();
            renderer.setDocumentFromString(html);
            renderer.layout();
            renderer.createPDF(outputStream);
            return outputStream.toByteArray();
        } catch (Exception e) {
            throw new RuntimeException("Erro ao gerar PDF do orcamento: " + e.getMessage(), e);
        }
    }
}