import React, { Component } from 'react';
import {Table} from '@bandwidth/shared-components';
import { validity } from './NumberLookupEnums';
import TableHeader from './TableHeader';
import FileDownload from "./FileDownload";
import styled from 'styled-components';



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
    (<Table.Cell key = {"5"}>{rateCenter.lata}</Table.Cell>),
    (<Table.Cell key = {"6"}>{rateCenter.ocn}</Table.Cell>),
    (<Table.Cell key = {"7"}>{rateCenter.aocn}</Table.Cell>),
    (<Table.Cell key = {"8"}>{rateCenter.rateCenter}</Table.Cell>),
    (<Table.Cell key = {"9"}>{rateCenter.state}</Table.Cell>),
    (<Table.Cell key = {"10"}>{rateCenter.ocnOverall}</Table.Cell>),
    (<Table.Cell key = {"11"}>{date.year + "-" + date.monthValue + "-" + date.dayOfMonth}</Table.Cell>)

  ]
}

function InvalidTableRow(props)
{
  return [
    (<Table.Cell key = {"1"}>{props.json.query}</Table.Cell>),
    (<Table.Cell key = {"3"} style = {{color:"#ff391a"}}>{props.json.status} </Table.Cell>)
  ]
}

const HeadingDiv = styled.div`
  display:flex;
  flex-direction:row;
  justify-content : space-between;
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
        <h1 style={{color:"#00aaa6c"}}>{"Query results:"} </h1>
        <HeadingDiv>
          <h3>{this.props.timestamp}</h3>
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

//make validity for each record instead of one record
//one ehader only