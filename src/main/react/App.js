import React, {Component} from 'react';
import NumberlookupPage from "./NumberlookupPage";
import {BrowserRouter} from "react-router-dom";


export default class App extends Component {

  render() {
    return (
        <BrowserRouter>
      <NumberlookupPage/>
        </BrowserRouter>
    );
  }

}