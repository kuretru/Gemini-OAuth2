import { Space, Tag } from 'antd';
import React from 'react';

export type PermissionLabelProps = {
  type: string;
  name: string;
};

const PermissionLabel: React.FC<PermissionLabelProps> = (props) => {
  return (
    <Space>
      <Tag color={props.type === '读取' ? 'green' : 'orange'}>
        {props.type} {props.name}
      </Tag>
    </Space>
  );
};
export default PermissionLabel;
