import React from "react";

import {Button, Callout, Alert} from '@bandwidth/shared-components';
import '@bandwidth/shared-components/fonts/fonts.css';


export default class ShareableLink extends React.Component
{

  constructor(props)
  {
    super(props);
    this.state = {shareableQueries:0, showAlert:false};
  }

  /**
   * Generates the shareable URL
   */
  createURL()
  {
    //this gives us domain+port or anything before query
    let urlBase  = window.location.href.split('/')[2];
    const queryStart = "/ratecenterlookup/number?phoneNumbers="

    //now need to make sure that the input is restricted to 2000 characters for the get request

    console.log(urlBase + queryStart + this.props.queryString);
    const url = this.shortenRequest(urlBase + queryStart, this.props.queryString);
    this.copyToClipboard(url);
    this.setState({showAlert:true});

  }

  /**
   * Truncates the url request to be shorter than 2000 characters
   */
  shortenRequest(path, queries)
  {
    let remainingSize = 2000 - path.length;
    let truncatedQueries = "";
    let splitQueries = queries.split(',');

    let i = 0;
    for(i= 0; i < splitQueries.length; i++)
    {
      if(truncatedQueries.length + splitQueries[i].length > remainingSize) break;
      truncatedQueries += splitQueries[i] + ",";
    }

    this.setState({shareableQueries:i});

    if(truncatedQueries[truncatedQueries.length - 1] === ',')
    {
      truncatedQueries = truncatedQueries.substring(0, truncatedQueries.length - 1);
    }

    return path + truncatedQueries;

  }

  /**
   * Copy variable to URL. Adapted from:
   * https://techoverflow.net/2018/03/30/copying-strings-to-the-clipboard-using-pure-javascript/
   * @param url
   */
  copyToClipboard(url)
  {
    let el = document.createElement('textarea');
    el.value = url;
    el.setAttribute('readonly', '');
    el.style = {position: 'absolute', left: '-9999px'};
    document.body.appendChild(el);
    el.select();
    document.execCommand('copy');
    document.body.removeChild(el);
  }

  //read input to create url
  render()
  {


    let alert;
    if(this.state.showAlert)
    {
      alert = <React.Fragment>
        <Alert.Group.Global>
          <Alert onClose={() => {this.setState({showAlert:false})}} type="success" closeTimeout={200000}> Link copied to clipboard successfully! </Alert>
        </Alert.Group.Global>
      </React.Fragment>
    }
    else
    {
      alert = "";
    }

    let calloutContent = "Please note that the shareable link will have a 2000 character limit. For larger datasets, please download and share the file.";
    return (
        <div>
        <Callout content = {calloutContent} placement = "left" >
            <Button style ={{marginBottom: '5px', marginRight:'5px'}} onClick = { () => {this.createURL()}}>Get Shareable Link</Button>
        </Callout>
          {alert}
        </div>
    );
  }
}
