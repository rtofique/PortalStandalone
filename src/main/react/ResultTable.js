import React, { Component } from 'react';
import {Button, Table, Callout} from '@bandwidth/shared-components';
import ShareableLink from './ShareableLink'
import { validity } from './NumberLookupEnums';
import TableHeader from './TableHeader';
import FileDownload from "./FileDownload";
import styled from 'styled-components';


/**
 * This component constructs the main body of the output by displaying the header, timestamp, and the table translated from the JSON output.
 *
 */

function ValidTableRow(props)
{
  let json = props.json;
  let rateCenter = json.rateCenter;
  let date = rateCenter.effectiveDate;
  let blockNumber = json.query.charAt(6);
  let npanxx = json.query.substring(0, 3) + "-" + json.query.substring(3, 6);


  return [
    (<Table.Cell key = {"2"}>{npanxx}</Table.Cell>),
    (<Table.Cell key = {"3"}>{blockNumber}</Table.Cell>),
    (<Table.Cell key = {"4"}>{json.recordFound.toString()}</Table.Cell>),
    (<Table.Cell key = {"5"}>{resolveField(rateCenter, 'lata')}</Table.Cell>),
    (<Table.Cell key = {"6"}>{resolveField(rateCenter, 'ocn')}</Table.Cell>),
    (<Table.Cell key = {"7"}>{resolveField(rateCenter, 'aocn')}</Table.Cell>),
    (<Table.Cell key = {"8"}>{resolveField(rateCenter, 'rateCenter')}</Table.Cell>),
    (<Table.Cell key = {"9"}>{resolveField(rateCenter, 'state')}</Table.Cell>),
    (<Table.Cell key = {"10"}>{resolveField(rateCenter, 'ocnOverall')}</Table.Cell>),
    (<Table.Cell key = {"11"}>{resolveField(date, 'year') + "-" + resolveField(date,'monthValue') + "-" + resolveField(date,'dayOfMonth')}</Table.Cell>)

  ]
}

function resolveField(parentField, childField)
{
  if(parentField)
  {
    return (parentField[childField]) ? parentField[childField] : "N/A";
  }
  return "N/A";
}

function InvalidTableRow(props)
{
  return [
    (<Table.Cell key = {"1"}>{props.json.query}</Table.Cell>),
    (<Table.Cell key = {"2"} style = {{color:"#ff391a"}}>{props.json.status} </Table.Cell>)

  ]
}

const HeadingDiv = styled.div`
  display:flex;
  flex-direction:row;
  justify-content: flex-start;
  & > * {
    margin-top: auto;
    margin-bottom: auto;
  }
`;



export default class ResultTable extends React.Component {

  constructor(props)
  {
    super(props);
    this.isAllInvalid = this.isAllInvalid.bind(this);

  }

  isAllInvalid()
  {
    for(let query in this.props.results)
    {
      if(this.props.results[query].jsonType !== "$INVALID")
      {
        return false;
      }
    }
    return true;
  }

  render()
  {
    //let color = (this.props.validity === validity.invalid) ? "#ff391a" : "#00aa6c";
    let counter =0;
    return(


      <div>
        <br/>
        <h1 style={{color:"#60545b"}}>{"Query Results"} </h1>
        <HeadingDiv>
          <h3 style={{marginRight:'auto', color:"#60545b"}}>{this.props.timestamp}</h3>
          <ShareableLink queryString = {this.props.queryString}/>
          <FileDownload jsonText = {this.props.results} timestamp = {this.props.timestamp} />
        </HeadingDiv>
        <Table
            headers = {
              <TableHeader isAllInvalid = {this.isAllInvalid}/>
            }>

          {this.props.results.map(record => (
              <Table.Row key = {++counter}>
                { (record.jsonType === validity.valid) ? (
                    <ValidTableRow json = {record} key = {++counter}/>
                ): (
                    <InvalidTableRow json = {record} key = {++counter}/>
                )}
              </Table.Row>
          ))}

        </Table>
      </div>

    );
  }

}
