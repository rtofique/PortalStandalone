import React, { Component } from 'react';
import {Table} from '@bandwidth/shared-components';
import { validity } from './NumberLookupEnums';


const VALID_HEADERS = [

  "ID",
  "Phone Number",
  "Rate Center(s)",
  "Record Found"
];

const INVALID_HEADERS = [
  "ID",
  "Phone Number",
  "Error"
];

export default class TableHeader extends React.Component {

    render()
    {
      console.log(this.props.validity);
      let headers = (this.props.validity === validity.valid) ? VALID_HEADERS : INVALID_HEADERS;

      return(

          <Table.Row>
            {headers.map(heading => (
                <Table.Header key = {heading}>{heading}</Table.Header>
            ))}
          </Table.Row>

      );
    }
}