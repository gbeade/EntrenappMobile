package com.example.entrenapp.recyclerView;

import java.util.List;

// Decimos que una estructura es 'Cardeable' si ofrece información para ser mostrada en una tarjeta de la forma
//                TITULO
// SUBTITULO: xxx
// SUBTITULO: yyy
// SUBTITULO: zzz
//     [ICON 1]  [ICON 2] [ICON 3]
// Debe devolver una lista ordenada de pares <String, String>
public interface Cardable {

    class CardCaption {
        private String captionTitle;
        private String xmlTagID;
        private String content;

        public CardCaption(String xmlTagID, String captionTitle, String content) {
            this.captionTitle = captionTitle;
            this.xmlTagID = xmlTagID;
            this.content = content;
        }

        public String getCaptionTitle() {
            return captionTitle;
        }

        public String getXmlTagID() {
            return xmlTagID;
        }

        public String getContent() {
            return content;
        }
    }

    class CardImage {
        String name;
        String xmlTagID;
        // String content;  TODO: habria que averiguar un poco mas sobre fotos
    }

    // Devuelve una lista de los captions de la tarjeta
    public List<CardCaption> getCaptions();

    // De haberlos, devuelve todos los íconos a mostrar en la carta. Lista vacía de no haberlos.
//    public List<Caption> getIcons();

    // Devuelve una lista con un sumario de iconos a pedido.
    // Si un ícono no existe, se lo ignora.
//    public List<Pair<String, String>> getIconSummary(String[] subtitles);
}
