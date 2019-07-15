import React, { Component } from 'react';
import ResultTable from "./ResultTable";
import FileDownload from "./FileDownload";

export default class NumberLookupOutput extends React.Component
{

	render()
	{

		return (
				<div>
					{this.props.output != "" &&
						<ResultTable results = {this.props.output} timestamp = {this.props.timestamp}/>
					}
				</div>
		);
	}
}


