import React from 'react';
import type { ProColumns } from '@ant-design/pro-table';
import { Image } from 'antd';
import BaseTableOnlyPage from '@/components/BaseTableOnlyPage';
import OAuthPermissionService from '@/services/gemini-oauth2/oauth/permission';
import PermissionLabel from './components/PermissionLabel';

const permissionChinese = {
  "email": "电子邮箱",
  "mobile": "手机号码"
};

class OAuthPermission extends React.Component {
  columns: ProColumns<API.OAuth.OAuthPermissionVO>[] = [
    {
      align: 'center',
      dataIndex: 'applicationName',
      search: false,
      title: '应用',
      width: 240,
      render: (_, record) => [
        <Image key="avatar" src={record.application.avatar} width={25} height={25} />,
        <a href={record.application.homepage} key="homepage" rel="noreferrer" target="_blank">
          {record.application.name}
        </a>,
      ]
    },
    {
      align: 'center',
      dataIndex: 'permissions',
      search: false,
      title: '权限',
      render: (_, record) => {
        const result: any[] = [];
        record.permissions.forEach(permission => {
          let type = "读取";
          if (permission.endsWith('_w')) {
            permission.replace('_w', '');
            type = "修改";
          }

          let name = "未知";
          for (const key in permissionChinese) {
            if (permission.startsWith(key)) {
              name = permissionChinese[key];
              break;
            }
          }

          result.push(<PermissionLabel type={type} name={name} />);
        })
        return result;
      }
    },
  ];

  render() {
    return (
      <BaseTableOnlyPage<API.OAuth.OAuthPermissionVO, API.OAuth.OAuthPermissionQuery>
        pageName="我授权的应用"
        service={new OAuthPermissionService()}
        columns={this.columns}
        deleteButtonText={"取消授权"}
        search={false}
      />
    );
  }
}

export default OAuthPermission;
