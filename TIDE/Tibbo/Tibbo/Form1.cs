using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Linq;
using System.Text;
using System.Windows.Forms;

namespace Tibbo
{
    public partial class Form1 : Form
    {
        String url;
        public Form1()
        {
            InitializeComponent();
            url = "192.168.0.104";
        }

        private void toolStripButton1_Click(object sender, EventArgs e)
        {
            webBrowser1.Navigate(url);
        }

        private void toolStripButton2_Click(object sender, EventArgs e)
        {
            webBrowser1.Navigate(url + "/httpPost.html");
        }

        private void toolStripButton3_Click(object sender, EventArgs e)
        {
            webBrowser1.Navigate(url + "/message.html");
        }

        private void toolStripButton4_Click(object sender, EventArgs e)
        {
            webBrowser1.Navigate(url + "/data.html");
        }

        private void Form1_Deactivate(object sender, EventArgs e)
        {
            WindowState = FormWindowState.Minimized;
        }
    }
}
