import React, { Component } from 'react';
import {Table} from '@bandwidth/shared-components';
import { validity } from './NumberLookupEnums';
import TableHeader from './TableHeader';


function ValidTableRow(props)
{
  return [
    (<Table.Cell key = {"1"}>1</Table.Cell>),
    (<Table.Cell key = {"2"}>{props.json.query}</Table.Cell>),
    (<Table.Cell key = {"3"}>Rate Centers </Table.Cell>),
    (<Table.Cell key = {"4"}> {props.json.recordFound.toString()}</Table.Cell>)
  ]
}

function InvalidTableRow(props)
{
  return [
    (<Table.Cell key = {"1"}>{props.json.queryID}</Table.Cell>),
    (<Table.Cell key = {"2"}>{props.json.query}</Table.Cell>),
    (<Table.Cell key = {"3"}>{props.json.status} </Table.Cell>)
  ]
}



export default class ResultTable extends React.Component {


  resolveHeading()
  {
    switch (this.props.validity) {
      case validity.empty:
        return "";
      case validity.valid:
        return "Query Resolved Properly";
        break;
      case validity.invalid:
        return "Improper phone numbers in query. See below for more details";
    }
  }



  render()
  {
    console.log(this.props.results);
    return(


      <div>
        <h1>{this.resolveHeading()} </h1>
        <Table
            headers = {
              <TableHeader validity = {this.props.validity}> </TableHeader>
            }>

          {this.props.results.map(record => (
              <Table.Row key = {record.query}>
                { (this.props.validity === validity.valid) ? (
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

