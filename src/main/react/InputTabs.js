import React, { Component } from 'react';


export default class InputTabs extends React.Component
{
  constructor(props) {
    super(props);

    this.state = { selectedIndex: 0 };
    this.handleSelect = (index) => {
      this.state.selectedIndex = index;
      this.setState(this.state);
    };
  }

  render() {
    const { Group, Tab, Container, contents} = this.props;

    return (
        <Container>
          <Group
              selectedTabIndex={this.state.selectedIndex}
              onTabSelected={this.handleSelect}
          >
            <Tab>Enter Text</Tab>
            <Tab>Upload Files</Tab>
          </Group>
          <Tab.Content>
            {contents[this.state.selectedIndex]}
          </Tab.Content>
        </Container>
    );
  }
}