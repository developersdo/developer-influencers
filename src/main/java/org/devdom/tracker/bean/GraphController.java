package org.devdom.tracker.bean;

import java.io.Serializable;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import org.devdom.tracker.model.dao.GraphDao;
import org.devdom.tracker.model.dto.GraphCommentsStat;
import org.devdom.tracker.model.dto.GraphPostsStat;
 
/**
 *
 * @author Carlos Vasquez Polanco
 */
@ManagedBean
@RequestScoped
public class GraphController  implements Serializable{
    
    private final GraphDao dao = new GraphDao();
    
    /**
     * Obtener valores para formar el gráfico de la cantidad de publicaciones por meses
     * @return 
     */
    public List<GraphPostsStat> getPostsStats(){
        return dao.findPostsStats();
    }

    /**
     * Obtener valores para formar el gráfico de la cantidad de comentarios por meses
     * @return 
     */
    public List<GraphCommentsStat> getCommentsStats(){
        return dao.findCommentsStats();
    }
    
    /**
     * Codigo del que no estoy orgulloso, pero fue la forma más óptima posible de renderizar la  gráfica de comentarios
     * 
     * @return 
     */
    public String getCommentsGraph(){
        String html = "<script type=\"text/javascript\">\n" +
"                                    AmCharts.makeChart(\"chartComments\",\n" +
"                                            {\n" +
"                                                    \"type\": \"serial\",\n" +
"                                                    \"pathToImages\": \"http://cdn.amcharts.com/lib/3/images/\",\n" +
"                                                    \"categoryField\": \"category\",\n" +
"                                                     \"sequencedAnimation\": false,\n"+
"                                                    \"categoryAxis\": {\n" +
"                                                            \"gridPosition\": \"start\"\n" +
"                                                    },\n" +
"                                                    \"trendLines\": [],\n" +
"                                                    \"graphs\": [\n" +
"                                                            {\n" +
"                                                                \"balloonText\": \"[[value]] comentarios en [[category]] por [[title]]\",\n" +
"                                                                \"bullet\": \"round\",\n" +
"                                                                \"id\": \"201514949865358\",\n" +
"                                                                \"title\": \"Developers Dominicanos\",\n" +
"                                                                \"type\": \"smoothedLine\",\n" +
"                                                                \"valueField\": \"column-201514949865358\"\n" +
"                                                            },\n" +
"                                                            {\n" +
"                                                                \"balloonText\": \"[[value]] comentarios en [[category]] por [[title]]\",\n" +
"                                                                \"bullet\": \"round\",\n" +
"                                                                \"id\": \"161328360736390\",\n" +
"                                                                \"title\": \"Hackers and Founders - Santo Domingo\",\n" +
"                                                                \"type\": \"smoothedLine\",\n" +
"                                                                \"valueField\": \"column-161328360736390\"\n" +
"                                                            },\n" +
"                                                            {\n" +
"                                                                \"balloonText\": \"[[value]] comentarios en [[category]] por [[title]]\",\n" +
"                                                                \"bullet\": \"round\",\n" +
"                                                                \"id\": \"137759453068575\",\n" +
"                                                                \"title\": \"Python Dominicana\",\n" +
"                                                                \"type\": \"smoothedLine\",\n" +
"                                                                \"valueField\": \"column-137759453068575\"\n" +
"                                                            },\n" +
"                                                            {\n" +
"                                                                \"balloonText\": \"[[value]] comentarios en [[category]] por [[title]]\",\n" +
"                                                                \"bullet\": \"round\",\n" +
"                                                                \"id\": \"264382946926439\",\n" +
"                                                                \"title\": \"#VivaPHP\",\n" +
"                                                                \"type\": \"smoothedLine\",\n" +
"                                                                \"valueField\": \"column-264382946926439\"\n" +
"                                                            },\n" +
"                                                            {\n" +
"                                                                \"balloonText\": \"[[value]] comentarios en [[category]] por [[title]]\",\n" +
"                                                                \"bullet\": \"round\",\n" +
"                                                                \"id\": \"132533423551389\",\n" +
"                                                                \"title\": \"developers X\",\n" +
"                                                                \"type\": \"smoothedLine\",\n" +
"                                                                \"valueField\": \"column-132533423551389\"\n" +
"                                                            },\n" +
"                                                            {\n" +
"                                                                \"balloonText\": \"[[value]] comentarios en [[category]] por [[title]]\",\n" +
"                                                                \"bullet\": \"round\",\n" +
"                                                                \"id\": \"358999187465748\",\n" +
"                                                                \"title\": \"CodigoLibre_Developers\",\n" +
"                                                                \"type\": \"smoothedLine\",\n" +
"                                                                \"valueField\": \"column-358999187465748\"\n" +
"                                                            },\n" +
"                                                            {\n" +
"                                                                \"balloonText\": \"[[value]] comentarios en [[category]] por [[title]]\",\n" +
"                                                                \"bullet\": \"round\",\n" +
"                                                                \"id\": \"179210165492903\",\n" +
"                                                                \"title\": \"Caribbean SQL\",\n" +
"                                                                \"type\": \"smoothedLine\",\n" +
"                                                                \"valueField\": \"column-179210165492903\"\n" +
"                                                            },\n" +
"                                                            {\n" +
"                                                                \"balloonText\": \"[[value]] comentarios en [[category]] por [[title]]\",\n" +
"                                                                \"bullet\": \"round\",\n" +
"                                                                \"id\": \"150647751783730\",\n" +
"                                                                \"title\": \"Javascript Dominicana\",\n" +
"                                                                \"type\": \"smoothedLine\",\n" +
"                                                                \"valueField\": \"column-150647751783730\"\n" +
"                                                            },\n" +
"                                                            {\n" +
"                                                                \"balloonText\": \"[[value]] comentarios en [[category]] por [[title]]\",\n" +
"                                                                \"bullet\": \"round\",\n" +
"                                                                \"id\": \"455974804478621\",\n" +
"                                                                \"title\": \"Mobile Developer Group\",\n" +
"                                                                \"type\": \"smoothedLine\",\n" +
"                                                                \"valueField\": \"column-455974804478621\"\n" +
"                                                            },\n" +
"                                                            {\n" +
"                                                                \"balloonText\": \"[[value]] comentarios en [[category]] por [[title]]\",\n" +
"                                                                \"bullet\": \"round\",\n" +
"                                                                \"id\": \"220361121324698\",\n" +
"                                                                \"title\": \"DevelopersRD\",\n" +
"                                                                \"type\": \"smoothedLine\",\n" +
"                                                                \"valueField\": \"column-220361121324698\"\n" +
"                                                            },\n" +
"                                                            {\n" +
"                                                                \"balloonText\": \"[[value]] comentarios en [[category]] por [[title]]\",\n" +
"                                                                \"bullet\": \"round\",\n" +
"                                                                \"id\": \"634620033215438\",\n" +
"                                                                \"title\": \"C#.do\",\n" +
"                                                                \"type\": \"smoothedLine\",\n" +
"                                                                \"valueField\": \"column-634620033215438\"\n" +
"                                                            }\n" +
"                                                    ],\n" +
"                                                    \"guides\": [],\n" +
"                                                    \"valueAxes\": [\n" +
"                                                            {\n" +
"                                                                    \"id\": \"ValueAxis-1\",\n" +
"                                                                    \"title\": \"Comentarios\"\n" +
"                                                            }\n" +
"                                                    ],\n" +
"                                                    \"allLabels\": [],\n" +
"                                                    \"balloon\": {},\n" +
"                                                    \"legend\": {\n" +
"                                                            \"useGraphSettings\": true\n" +
"                                                    },\n" +
"                                                    \"titles\": [\n" +
"                                                            {\n" +
"                                                                    \"id\": \"Title-1\",\n" +
"                                                                    \"size\": 15,\n" +
"                                                                    \"text\": \"Gráfica de actividad mes por mes de los comentarios creados en los distintos grupos de desarrollo\"\n" +
"                                                            }\n" +
"                                                    ],\n" +
"                                                    \"dataProvider\": [\n";

            String monthNameTemp = "";
            int counter = 0;
            for(GraphCommentsStat graph : getCommentsStats()){
                if(!monthNameTemp.equals(graph.getMonths())){
                    if(counter==0)
                        html +="{";
                    else{
                        html +="},{";
                    }
                }

                html += "'category': '"+graph.getMonths()+" "+ graph.getYears()+"',\n";
                html += "'"+graph.getGroupColumnId()+"': "+graph.getAmount()+", \n";
                monthNameTemp = graph.getMonths();
                counter++;
            }
            html += "}";

            html += "            ]\n" +
                    "        }\n" +
                    "   );\n" +
                    "</script>";
        return html;
    }
    
    /**
     * Codigo del que no estoy orgulloso, pero fue la forma más óptima posible de renderizar la  gráfica de comentarios
     * 
     * @return 
     */
    public String getPostsGraph(){

        String html = "<script type=\"text/javascript\">\n" +
"                                    AmCharts.makeChart(\"chartPosts\",\n" +
"                                            {\n" +
"                                                    \"type\": \"serial\",\n" +
"                                                    \"pathToImages\": \"http://cdn.amcharts.com/lib/3/images/\",\n" +
"                                                    \"categoryField\": \"category\",\n" +
"                                                     \"sequencedAnimation\": false,\n"+
"                                                    \"categoryAxis\": {\n" +
"                                                            \"gridPosition\": \"start\"\n" +
"                                                    },\n" +
"                                                    \"trendLines\": [],\n" +
"                                                    \"graphs\": [\n" +
"                                                            {\n" +
"                                                                \"balloonText\": \"[[value]] post en [[category]] por [[title]]\",\n" +
"                                                                \"bullet\": \"round\",\n" +
"                                                                \"id\": \"201514949865358\",\n" +
"                                                                \"title\": \"Developers Dominicanos\",\n" +
"                                                                \"type\": \"smoothedLine\",\n" +
"                                                                \"valueField\": \"column-201514949865358\"\n" +
"                                                            },\n" +
"                                                            {\n" +
"                                                                \"balloonText\": \"[[value]] post en [[category]] por [[title]]\",\n" +
"                                                                \"bullet\": \"round\",\n" +
"                                                                \"id\": \"161328360736390\",\n" +
"                                                                \"title\": \"Hackers and Founders - Santo Domingo\",\n" +
"                                                                \"type\": \"smoothedLine\",\n" +
"                                                                \"valueField\": \"column-161328360736390\"\n" +
"                                                            },\n" +
"                                                            {\n" +
"                                                                \"balloonText\": \"[[value]] post en [[category]] por [[title]]\",\n" +
"                                                                \"bullet\": \"round\",\n" +
"                                                                \"id\": \"137759453068575\",\n" +
"                                                                \"title\": \"Python Dominicana\",\n" +
"                                                                \"type\": \"smoothedLine\",\n" +
"                                                                \"valueField\": \"column-137759453068575\"\n" +
"                                                            },\n" +
"                                                            {\n" +
"                                                                \"balloonText\": \"[[value]] post en [[category]] por [[title]]\",\n" +
"                                                                \"bullet\": \"round\",\n" +
"                                                                \"id\": \"264382946926439\",\n" +
"                                                                \"title\": \"#VivaPHP\",\n" +
"                                                                \"type\": \"smoothedLine\",\n" +
"                                                                \"valueField\": \"column-264382946926439\"\n" +
"                                                            },\n" +
"                                                            {\n" +
"                                                                \"balloonText\": \"[[value]] post en [[category]] por [[title]]\",\n" +
"                                                                \"bullet\": \"round\",\n" +
"                                                                \"id\": \"132533423551389\",\n" +
"                                                                \"title\": \"developers X\",\n" +
"                                                                \"type\": \"smoothedLine\",\n" +
"                                                                \"valueField\": \"column-132533423551389\"\n" +
"                                                            },\n" +
"                                                            {\n" +
"                                                                \"balloonText\": \"[[value]] post en [[category]] por [[title]]\",\n" +
"                                                                \"bullet\": \"round\",\n" +
"                                                                \"id\": \"358999187465748\",\n" +
"                                                                \"title\": \"CodigoLibre_Developers\",\n" +
"                                                                \"type\": \"smoothedLine\",\n" +
"                                                                \"valueField\": \"column-358999187465748\"\n" +
"                                                            },\n" +
"                                                            {\n" +
"                                                                \"balloonText\": \"[[value]] post en [[category]] por [[title]]\",\n" +
"                                                                \"bullet\": \"round\",\n" +
"                                                                \"id\": \"179210165492903\",\n" +
"                                                                \"title\": \"Caribbean SQL\",\n" +
"                                                                \"type\": \"smoothedLine\",\n" +
"                                                                \"valueField\": \"column-179210165492903\"\n" +
"                                                            },\n" +
"                                                            {\n" +
"                                                                \"balloonText\": \"[[value]] post en [[category]] por [[title]]\",\n" +
"                                                                \"bullet\": \"round\",\n" +
"                                                                \"id\": \"150647751783730\",\n" +
"                                                                \"title\": \"Javascript Dominicana\",\n" +
"                                                                \"type\": \"smoothedLine\",\n" +
"                                                                \"valueField\": \"column-150647751783730\"\n" +
"                                                            },\n" +
"                                                            {\n" +
"                                                                \"balloonText\": \"[[value]] post en [[category]] por [[title]]\",\n" +
"                                                                \"bullet\": \"round\",\n" +
"                                                                \"id\": \"455974804478621\",\n" +
"                                                                \"title\": \"Mobile Developer Group\",\n" +
"                                                                \"type\": \"smoothedLine\",\n" +
"                                                                \"valueField\": \"column-455974804478621\"\n" +
"                                                            },\n" +
"                                                            {\n" +
"                                                                \"balloonText\": \"[[value]] post en [[category]] por [[title]]\",\n" +
"                                                                \"bullet\": \"round\",\n" +
"                                                                \"id\": \"220361121324698\",\n" +
"                                                                \"title\": \"DevelopersRD\",\n" +
"                                                                \"type\": \"smoothedLine\",\n" +
"                                                                \"valueField\": \"column-220361121324698\"\n" +
"                                                            },\n" +
"                                                            {\n" +
"                                                                \"balloonText\": \"[[value]] post en [[category]] por [[title]]\",\n" +
"                                                                \"bullet\": \"round\",\n" +
"                                                                \"id\": \"634620033215438\",\n" +
"                                                                \"title\": \"C#.do\",\n" +
"                                                                \"type\": \"smoothedLine\",\n" +
"                                                                \"valueField\": \"column-634620033215438\"\n" +
"                                                            }\n" +
"                                                    ],\n" +
"                                                    \"guides\": [],\n" +
"                                                    \"valueAxes\": [\n" +
"                                                            {\n" +
"                                                                    \"id\": \"ValueAxis-1\",\n" +
"                                                                    \"title\": \"Publicaciones\"\n" +
"                                                            }\n" +
"                                                    ],\n" +
"                                                    \"allLabels\": [],\n" +
"                                                    \"balloon\": {},\n" +
"                                                    \"legend\": {\n" +
"                                                            \"useGraphSettings\": true\n" +
"                                                    },\n" +
"                                                    \"titles\": [\n" +
"                                                            {\n" +
"                                                                    \"id\": \"Title-1\",\n" +
"                                                                    \"size\": 15,\n" +
"                                                                    \"text\": \"Gráfica de actividad mes por mes de las publicaciones creadas en los distintos grupos de desarrollo\"\n" +
"                                                            }\n" +
"                                                    ],\n" +
"                                                    \"dataProvider\": [\n";
            
            String monthNameTemp = "";
            int counter = 0;
            for(GraphPostsStat graph : getPostsStats()){
                if(!monthNameTemp.equals(graph.getMonths())){
                    if(counter==0)
                        html +="{";
                    else{
                        html +="},{";
                    }
                }

                html += "'category': '"+graph.getMonths()+" "+ graph.getYears()+"',\n";
                html += "'"+graph.getGroupColumnId()+"': "+graph.getAmount()+", \n";
                monthNameTemp = graph.getMonths();
                counter++;
            }
            html += "}";

            html += "            ]\n" +
                    "        }\n" +
                    "   );\n" +
                    "</script>";
        return html;
    }
    
}
