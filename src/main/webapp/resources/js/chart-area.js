// Revenue Area Chart Configuration
// NOTE: revenueLabels and revenueData must be prepared in JSP before loading this script

// Plugin to display data labels on points
Chart.plugins.register({
  afterDatasetsDraw: function (chart) {
    var ctx = chart.ctx;
    chart.data.datasets.forEach(function (dataset, i) {
      var meta = chart.getDatasetMeta(i);
      if (!meta.hidden) {
        meta.data.forEach(function (element, index) {
          // Draw the text
          ctx.fillStyle = "rgb(0, 0, 0)";
          ctx.font = Chart.helpers.fontString(
            11,
            "bold",
            Chart.defaults.global.defaultFontFamily
          );
          ctx.textAlign = "center";
          ctx.textBaseline = "bottom";

          var dataString = dataset.data[index].toLocaleString("vi-VN");
          ctx.fillText(
            dataString + " đ",
            element._model.x,
            element._model.y - 5
          );
        });
      }
    });
  },
});

// Render Area Chart
var ctx = document.getElementById("myAreaChart");
var myLineChart = new Chart(ctx, {
  type: "line",
  data: {
    labels: revenueLabels,
    datasets: [
      {
        label: "Doanh thu (VNĐ)",
        lineTension: 0.3,
        backgroundColor: "rgba(2,117,216,0.2)",
        borderColor: "rgba(2,117,216,1)",
        pointRadius: 5,
        pointBackgroundColor: "rgba(2,117,216,1)",
        pointBorderColor: "rgba(255,255,255,0.8)",
        pointHoverRadius: 5,
        pointHoverBackgroundColor: "rgba(2,117,216,1)",
        pointBorderWidth: 2,
        data: revenueData,
      },
    ],
  },
  options: {
    scales: {
      xAxes: [
        {
          time: {
            unit: "date",
          },
          gridLines: {
            display: false,
          },
          ticks: {
            maxTicksLimit: 7,
          },
        },
      ],
      yAxes: [
        {
          ticks: {
            min: 0,
            maxTicksLimit: 5,
            callback: function (value, index, values) {
              return value.toLocaleString("vi-VN") + " đ";
            },
          },
          gridLines: {
            color: "rgba(0, 0, 0, .125)",
          },
        },
      ],
    },
    legend: {
      display: true,
    },
    tooltips: {
      callbacks: {
        label: function (tooltipItem, chart) {
          var datasetLabel =
            chart.datasets[tooltipItem.datasetIndex].label || "";
          return (
            datasetLabel +
            ": " +
            tooltipItem.yLabel.toLocaleString("vi-VN") +
            " đ"
          );
        },
      },
    },
  },
});
