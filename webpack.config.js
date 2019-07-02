const webpack = require('webpack');
const path = require('path');

//no double quotes on this one
const getPublicPath = env => {
  return 'http://localhost:8080/'; // Used for local, used by default
};

module.exports = env => {

  return {
    entry: "./src/main/react/index.js",
    devtool: "sourcemaps",
    output: {
      publicPath: getPublicPath(env),
      filename: "bundle.js",
      path: __dirname + "/target/classes/static",
    },
    module: {
      rules: [
        {
          test: /\.css$/,
          use: [
            {loader: "style-loader"},
            {loader: "css-loader"}
          ]
        },
        {
          test: /\.(js|jsx)$/,
          exclude: /node_modules/,
          use: {
            loader: 'babel-loader',
            options: {
              presets: [
                "env",
                'react',
                'stage-0',
              ]
            }
          }
        },
        {
          test: /\.(jpg|jpeg|png|webp|ico|svg|woff|woff2|eot|otf|ttf)$/,
          use: [
            {
              loader: 'file-loader',
            },
          ],
        },
      ]
    },
  }
};
