import React, { Component } from 'react';
import {Table} from '@bandwidth/shared-components';
import { validity } from './NumberLookupEnums';


const VALID_HEADERS = [

  "NPA-NXX",
  "Block Number",
  "Record Found",
  "LATA",
  "OCN",
  "AOCN",
  "Rate Center",
  "State",
  "OCN Overall",
  "Date"
];

/*const INVALID_HEADERS = [
  "Query Number",
  "Query",
  "Error"
];*/

/**
 * Writes the headers for the results table. Initially, we had two separate headers for valid and invalid queries which explains the artifacts.
 */

export default class TableHeader extends React.Component {

    render()
    {
      //console.log(this.props.validity);
      //let headers = (this.props.validity === validity.valid) ? VALID_HEADERS : INVALID_HEADERS;

      let headers = VALID_HEADERS;

      return(

          <Table.Row>
            {headers.map(heading => (
                <Table.Header key = {heading}>{heading}</Table.Header>
            ))}
          </Table.Row>

      );
    }
}