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
	
	// 生成显示图表的面板
	public static JPanel createXYLine(String chartName,String xName,String yName,Map<String, List<Double>> data) {
		XYDataset dataSet = createDataset(data);
		JFreeChart jfreechart = createChart(chartName,xName,yName,dataSet,true);
		return new ChartPanel(jfreechart);
	}
	// 生成显示图表的面板
	public static JPanel createXYLine(String chartName,String xName,String yName,Map<String, List<Double>> data,boolean showLegend) {
		XYDataset dataSet = createDataset(data);
		JFreeChart jfreechart = createChart(chartName,xName,yName,dataSet,showLegend);
		return new ChartPanel(jfreechart);
	}

	// 生成图表主对象JFreeChart
	public static JFreeChart createChart(String chartName,String xName,String yName,XYDataset linedataset,boolean showLegend) {
		// 定义图表对象
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
		renderer.setBaseStroke( new BasicStroke(2.0f,     // 线条粗细
			      BasicStroke.CAP_ROUND,     // 端点风格
			      BasicStroke.JOIN_ROUND,     // 折点风格
			      8.f,     // 折点处理办法
			      dashes,     // 虚线数组
			      0.0f));
		//黑白的线,用形状来区分
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
		
		plot.setBackgroundPaint(Color.WHITE);									//背景色
		plot.getRangeAxis().setTickLabelFont(new Font("黑体", Font.PLAIN, 10)); //纵坐标字体设置
		plot.getRangeAxis().setLabelFont(new Font("宋体", Font.BOLD, 15)); 		//纵标题字体设置
		plot.getDomainAxis().setTickLabelFont(new Font("新宋体", 1, 10)); 		//横坐标字体设置
		plot.getDomainAxis().setLabelFont(new Font("新宋体", 1, 15)); 			//横标题字体设置
		plot.setDomainGridlinesVisible(true);									//横向网格线
		plot.setDomainGridlinePaint(Color.gray);
		plot.setRangeGridlinesVisible(true);									//纵向网格线
		plot.setRangeGridlinePaint(Color.gray);
		
		NumberAxis rangeAxis = (NumberAxis) plot.getRangeAxis();
		rangeAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());
		rangeAxis.setAutoRangeIncludesZero(true);
		rangeAxis.setUpperMargin(0.20);
		//rangeAxis.setLabelAngle(Math.PI / 2.0);
		
		chart.getLegend().setItemFont(new Font("宋体", Font.ITALIC | Font.BOLD, 15)); //分类的框内字体设置
		chart.getLegend().setVisible(showLegend);
		chart.getTitle().setFont(new Font("黑体", Font.BOLD, 17));				//总标题字体设置
		
		return chart;
	}

	// 生成数据
	public static XYSeriesCollection createDataset(Map<String, List<Double>> data) {
		//创建dataset
		XYSeriesCollection dataset = new XYSeriesCollection();
		//初始化dataset
		Set<String> keySet = data.keySet();
		for(String lineName : keySet){
			// 各曲线名称
			XYSeries lineSeries = new XYSeries(lineName);
			// 各曲线的值
			List<Double> lineData = data.get(lineName);
			//int step = lineData.size()/50;
			int step = 1;
			for(int i=0;i<lineData.size();i=i+step){
				lineSeries.add(i,lineData.get(i));
			}
			// 加到DataSet中
			dataset.addSeries(lineSeries);
		}
		//返回dataset
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
			stroke[i] = new BasicStroke(1.6f,     // 线条粗细
			      BasicStroke.CAP_ROUND,     // 端点风格
			      BasicStroke.JOIN_ROUND,     // 折点风格
			      8.f,     // 折点处理办法
			      dashes[i%6],     // 虚线数组
			      0.0f);
		}
		return stroke;
	}

}