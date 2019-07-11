import React, { Component } from 'react';
import {Table} from '@bandwidth/shared-components';
import { validity } from './NumberLookupEnums';
import TableHeader from './TableHeader';


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



export default class ResultTable extends React.Component {


  render()
  {
    //let color = (this.props.validity === validity.invalid) ? "#ff391a" : "#00aa6c";
    return(


      <div>
        <h1 style={{color:"#00aaa6c"}}>{"Query results:"} </h1>
        <h3>{this.props.timestamp}</h3>
        <Table
            headers = {
              <TableHeader />
            }>

          {this.props.results.map(record => (
              <Table.Row key = {record.query}>
                { (record.jsonType === validity.valid) ? (
                    <ValidTableRow json = {record} key = {record.query}/>
                ): (
                    <InvalidTableRow json = {record} key = {record.query}/>
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