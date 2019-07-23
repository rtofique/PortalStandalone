import React from "react";

import {Button} from '@bandwidth/shared-components';


export default class ShareableLink extends React.Component
{

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

    //give a good alert simply
  }

  /**
   * Truncates the url request to be shorter than 2000 characters
   */
  shortenRequest(path, queries)
  {
    let remainingSize = 2000 - path.length;
    let truncatedQueries = "";
    let splitQueries = queries.split(',');

    for(let i= 0; i < splitQueries.length; i++)
    {
      if(truncatedQueries.length + splitQueries[i].length > remainingSize) break;
      truncatedQueries += splitQueries[i] + ",";
    }

    if(truncatedQueries[truncatedQueries.length - 1] === ',')
    {
      truncatedQueries = truncatedQueries.substring(0, truncatedQueries.length - 1);
    }

    this.copyToClipboard(path + truncatedQueries);
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
    return (
     <Button style ={{marginBottom: '5', alignSelf:'flex-end'}} onClick = { () => {this.createURL()}}>Share</Button>
    );
  }
}
