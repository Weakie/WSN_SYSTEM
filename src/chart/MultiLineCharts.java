package chart;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.swing.JPanel;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;


public class MultiLineCharts{
	
	// ������ʾͼ������
	public static JPanel createXYLine(String chartName,String xName,String yName,Map<String, List<Double>> data) {
		XYDataset dataSet = createDataset(data);
		JFreeChart jfreechart = createChart(chartName,xName,yName,dataSet,true);
		return new ChartPanel(jfreechart);
	}
	// ������ʾͼ������
	public static JPanel createXYLine(String chartName,String xName,String yName,Map<String, List<Double>> data,boolean showLegend) {
		XYDataset dataSet = createDataset(data);
		JFreeChart jfreechart = createChart(chartName,xName,yName,dataSet,showLegend);
		return new ChartPanel(jfreechart);
	}

	// ����ͼ��������JFreeChart
	public static JFreeChart createChart(String chartName,String xName,String yName,XYDataset linedataset,boolean showLegend) {
		// ����ͼ�����
		JFreeChart chart = ChartFactory.createXYLineChart(chartName, // chart title
				xName, // domain axis label
				yName, // range axis label
				linedataset, // data
				PlotOrientation.VERTICAL, // orientation
				true, // include legend
				true, // tooltips
				false // urls
				);
		XYPlot plot = chart.getXYPlot();
		XYLineAndShapeRenderer   renderer = (XYLineAndShapeRenderer)plot.getRenderer();
		float dashes[] = {1};
		renderer.setBaseStroke( new BasicStroke(2.0f,     // ������ϸ
			      BasicStroke.CAP_ROUND,     // �˵���
			      BasicStroke.JOIN_ROUND,     // �۵���
			      8.f,     // �۵㴦��취
			      dashes,     // ��������
			      0.0f));
		//�ڰ׵���,����״������
		/*renderer.setBaseShapesVisible(true);
		//renderer.setBaseShapesFilled(false);
		renderer.setBaseItemLabelsVisible(true);
		renderer.setAutoPopulateSeriesOutlineStroke(true);
		int count = plot.getSeriesCount();
		//BasicStroke[] stroke = getStrokes(count);
		for(int i=0;i<count;i++){
			//renderer.setSeriesStroke(i, stroke[i]);
			renderer.setSeriesPaint(i, Color.black);
			renderer.setSeriesShapesFilled(i, (i%2==0));
		}*/
		
		plot.setBackgroundPaint(Color.WHITE);									//����ɫ
		plot.getRangeAxis().setTickLabelFont(new Font("����", Font.PLAIN, 10)); //��������������
		plot.getRangeAxis().setLabelFont(new Font("����", Font.BOLD, 15)); 		//�ݱ�����������
		plot.getDomainAxis().setTickLabelFont(new Font("������", 1, 10)); 		//��������������
		plot.getDomainAxis().setLabelFont(new Font("������", 1, 15)); 			//�������������
		plot.setDomainGridlinesVisible(true);									//����������
		plot.setDomainGridlinePaint(Color.gray);
		plot.setRangeGridlinesVisible(true);									//����������
		plot.setRangeGridlinePaint(Color.gray);
		
		NumberAxis rangeAxis = (NumberAxis) plot.getRangeAxis();
		rangeAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());
		rangeAxis.setAutoRangeIncludesZero(true);
		rangeAxis.setUpperMargin(0.20);
		//rangeAxis.setLabelAngle(Math.PI / 2.0);
		
		chart.getLegend().setItemFont(new Font("����", Font.ITALIC | Font.BOLD, 15)); //����Ŀ�����������
		chart.getLegend().setVisible(showLegend);
		chart.getTitle().setFont(new Font("����", Font.BOLD, 17));				//�ܱ�����������
		
		return chart;
	}

	// ��������
	public static XYSeriesCollection createDataset(Map<String, List<Double>> data) {
		//����dataset
		XYSeriesCollection dataset = new XYSeriesCollection();
		//��ʼ��dataset
		Set<String> keySet = data.keySet();
		for(String lineName : keySet){
			// ����������
			XYSeries lineSeries = new XYSeries(lineName);
			// �����ߵ�ֵ
			List<Double> lineData = data.get(lineName);
			//int step = lineData.size()/50;
			int step = 1;
			for(int i=0;i<lineData.size();i=i+step){
				lineSeries.add(i,lineData.get(i));
			}
			// �ӵ�DataSet��
			dataset.addSeries(lineSeries);
		}
		//����dataset
		return dataset;
	}
	
	@SuppressWarnings("unused")
	private static BasicStroke[] getStrokes(int n){
		float dashes[][] = { {8.0f,3.0f,8.0f,3.0f},
				{2.0f,4.0f,2.0f,3.0f},
				{1.0f,1.0f,1.0f,1.0f},
				{10.0f,8.0f,9.0f,10.0f},
				{6.0f,6.0f,6.0f,6.0f},
				{10.0f,20.0f,6.0f,20.0f},
							}; 
		BasicStroke stroke[] = new BasicStroke[n];
		for(int i=0;i<n;i++){
			stroke[i] = new BasicStroke(1.6f,     // ������ϸ
			      BasicStroke.CAP_ROUND,     // �˵���
			      BasicStroke.JOIN_ROUND,     // �۵���
			      8.f,     // �۵㴦��취
			      dashes[i%6],     // ��������
			      0.0f);
		}
		return stroke;
	}

}