package chart;

import java.awt.Color;
import java.awt.Font;
import java.util.List;

import javax.swing.JPanel;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.CategoryLabelPositions;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.labels.StandardCategoryItemLabelGenerator;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;

public class BarCharts
{
	public static JPanel createCategoryBar(String chartName,String xName,String yName,List<Double> result) {
		CategoryDataset dataSet = createDataset(result);
		JFreeChart localJFreeChart = createChart(chartName,xName,yName,dataSet);
		return new ChartPanel(localJFreeChart);
	}

	private static JFreeChart createChart(String chartName,String xName,String yName,CategoryDataset paramCategoryDataset)
	{
		JFreeChart localJFreeChart = ChartFactory.createBarChart(chartName,
    		xName,
    		yName,
    		paramCategoryDataset,
    		PlotOrientation.VERTICAL,
    		true,
    		true,
    		false);
		CategoryPlot localCategoryPlot = (CategoryPlot)localJFreeChart.getPlot();
		localCategoryPlot.setBackgroundPaint(Color.WHITE);									//背景色
		localCategoryPlot.getRangeAxis().setTickLabelFont(new Font("黑体", Font.PLAIN, 10)); //纵坐标字体设置
		localCategoryPlot.getRangeAxis().setLabelFont(new Font("宋体", Font.BOLD, 15)); 		//纵标题字体设置
		localCategoryPlot.getDomainAxis().setTickLabelFont(new Font("新宋体", 1, 10)); 		//横坐标字体设置
		localCategoryPlot.getDomainAxis().setLabelFont(new Font("新宋体", 1, 15)); 			//横标题字体设置
		localCategoryPlot.setRangeGridlinesVisible(true);									//横向网格线
		localCategoryPlot.setRangeGridlinePaint(Color.gray);
		localJFreeChart.getLegend().setItemFont(new Font("宋体", Font.ITALIC | Font.BOLD, 15)); 	//分类的框内字体设置
		localJFreeChart.getLegend().setVisible(false);
		localJFreeChart.getTitle().setFont(new Font("黑体", Font.BOLD, 17));					//总标题字体设置

		NumberAxis localNumberAxis = (NumberAxis)localCategoryPlot.getRangeAxis();
		localNumberAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());
		localNumberAxis.setUpperMargin(0.15D);
		
		// 柱图的呈现器
		BarRenderer renderer = (BarRenderer) localCategoryPlot.getRenderer();
		renderer.setIncludeBaseInRange(true); 	// 显示每个柱的数值，并修改该数值的字体属性
		renderer.setMaximumBarWidth(0.05);		// 柱子最大宽度
		renderer.setItemMargin(0.05); 			// 组内柱子间隔为组宽的10%
		renderer.setBaseOutlinePaint(Color.BLACK); 	// 设置柱子边框颜色
		renderer.setDrawBarOutline(true);		 	// 设置柱子边框可见
		renderer.setBaseItemLabelGenerator(new StandardCategoryItemLabelGenerator());
		renderer.setBaseItemLabelsVisible(true,true);
   
		CategoryAxis localCategoryAxis = localCategoryPlot.getDomainAxis();
		localCategoryAxis.setCategoryLabelPositions(CategoryLabelPositions.STANDARD);
		
		
		return localJFreeChart;
	}
	
	private static CategoryDataset createDataset(List<Double> result)
	{
		DefaultCategoryDataset localDefaultCategoryDataset = new DefaultCategoryDataset();
		for(int i = 0; i < result.size(); i++){
			Double value = result.get(i);
			localDefaultCategoryDataset.addValue(value,"", (Integer)i);
		}
		return localDefaultCategoryDataset;
	}
}