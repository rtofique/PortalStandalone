import React, { Component } from 'react';
import {Table} from '@bandwidth/shared-components';
import { INVALID_HEADERS, VALID_HEADERS } from './NumberLookupEnums';




/**
 * Writes the headers for the results table. Invalid headers are shown if all the output is invalid otherwise the normal headers are shown.
 */

export default class TableHeader extends React.Component {

    render()
    {
      let headers = (this.props.isAllInvalid()) ? INVALID_HEADERS : VALID_HEADERS;


      return(

          <Table.Row>
            {headers.map(heading => (
                <Table.Header key = {heading}>{heading}</Table.Header>
            ))}
          </Table.Row>

      );
    }
}