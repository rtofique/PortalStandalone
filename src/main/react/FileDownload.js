import React from "react";
import {Button} from '@bandwidth/shared-components';
import { VALID_HEADERS } from './NumberLookupEnums';

/**
 * Component to transform the output table into a csv file and and download it
 */

export default class FileDownload extends React.Component
{


  createCSVTextFromJSON(outputJSON)
  {
    //write headers first

    let lines = [];

    let entriesArray = [];
    VALID_HEADERS.filter(header => entriesArray.push(header));
    let headersString = this.createLine(entriesArray);
    lines.push(headersString);


    for (let id in outputJSON)
    {
      entriesArray = [];
      let query = outputJSON[id];

      if(query.jsonType === "$INVALID")
      {
        entriesArray.push(query.query);
        entriesArray.push(query.status);
        lines.push(this.createLine(entriesArray));
        continue;
      }

      //NPA-NXX
      entriesArray.push(query.query.toString().substring(0, 6));
      //Block
      entriesArray.push(query.query.toString().substring(6, 7));
      entriesArray.push(query.recordFound);
      entriesArray.push(query.rateCenter.lata);
      entriesArray.push(query.rateCenter.ocn);
      entriesArray.push(query.rateCenter.aocn);
      entriesArray.push(query.rateCenter.rateCenter);
      entriesArray.push(query.rateCenter.state);
      entriesArray.push(query.rateCenter.ocnOverall);
      let date = query.rateCenter.effectiveDate;
      entriesArray.push(date.year + "-" + date.monthValue + "-" + date.dayOfMonth + " " + date.hour + ":" + date.minute + ":" + date.second + ":" + date.nano);
      lines.push(this.createLine(entriesArray));

    }
    return lines.join("");

  }



  createLine(arr)
  {

    let line = arr.join(",");
    line += '\n';
    return line;
  }

  downloadCSVFile(outputJSON, timestamp)
  {

    const file = new Blob([this.createCSVTextFromJSON(outputJSON)], {type :'text/csv'});
    const element = document.createElement("a");
    element.href = URL.createObjectURL(file);
    element.download = timestamp + ".csv";
    document.body.appendChild(element);
    element.click();
  }

  render()
  {
    return(

        <Button style={{"marginBottom": 5} }onClick = {() => { this.downloadCSVFile(this.props.jsonText, this.props.timestamp)}}> Save</Button>
    );
  }
}