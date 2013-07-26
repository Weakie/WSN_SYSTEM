package chart;

import java.awt.Color;
import java.awt.Font;

import javax.swing.JPanel;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;


public class LineCharts{
	

	// 生成显示图表的面板
	public static JPanel createXYLine(double[] fitness,double[] coverage,double[] distance) {
		JFreeChart jfreechart = createChart(createDataset(fitness,coverage,distance));
		return new ChartPanel(jfreechart);
	}
	// 生成图表主对象JFreeChart
	public static JFreeChart createChart(XYDataset linedataset) {
		// 定义图表对象
		JFreeChart chart = ChartFactory.createXYLineChart("算法收敛曲线", // chart title
				"迭代次数", // domain axis label
				"值(%)", // range axis label
				linedataset, // data
				PlotOrientation.VERTICAL, // orientation
				true, // include legend
				true, // tooltips
				false // urls
				);
		XYPlot plot = chart.getXYPlot();
		plot.setBackgroundPaint(Color.WHITE);									//背景色
		plot.getRangeAxis().setTickLabelFont(new Font("黑体", Font.PLAIN, 12)); //纵坐标字体设置
		plot.getRangeAxis().setLabelFont(new Font("宋体", Font.BOLD, 10)); //纵标题的字体设置
		plot.getDomainAxis().setTickLabelFont(new Font("新宋体", 1, 15)); //横坐标字体设置
		plot.getDomainAxis().setLabelFont(new Font("新宋体", 1, 12)); //横标题字体设置
		chart.getLegend().setItemFont(new Font("宋体", Font.ITALIC, 15)); //三个分类的框内字体设置
		chart.getTitle().setFont(new Font("黑体", Font.BOLD, 12));// 总标题字体设置
		
		NumberAxis rangeAxis = (NumberAxis) plot.getRangeAxis();
		rangeAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());
		rangeAxis.setAutoRangeIncludesZero(true);
		rangeAxis.setUpperMargin(0.20);
		rangeAxis.setLabelAngle(Math.PI / 2.0);

		return chart;
	}

	// 生成数据
	public static XYSeriesCollection createDataset(double[] fitness,double[] coverage,double[] distance) {
		XYSeriesCollection dataset = new XYSeriesCollection();
		// 各曲线名称
		XYSeries seriesFitness = new XYSeries("适应度(覆蓋率+200/移动距离)");
		XYSeries seriesCoverage = new XYSeries("覆蓋率");
		XYSeries seriesDistance = new XYSeries("20000/移动距离");
		
		// 各曲线的值
		for (int i = 0; i < coverage.length; i++) {
			fitness[i]=(fitness[i]>1.5?1.5:fitness[i]);
			seriesFitness.add(i, fitness[i] * 100);
		}
		for (int i = 0; i < coverage.length; i++) {
			coverage[i]=(coverage[i]>1.5?1.5:coverage[i]);
			seriesCoverage.add(i, coverage[i] * 100);
		}
		for (int i = 0; i < coverage.length; i++) {
			distance[i]=(100/distance[i]>1.5?100/1.5:distance[i]);
			seriesDistance.add(i, 200 / distance[i] * 100);
		}
		// 加到DataSet中
		dataset.addSeries(seriesFitness);
		dataset.addSeries(seriesCoverage);
		dataset.addSeries(seriesDistance);
		
		return dataset;
	}
	

}