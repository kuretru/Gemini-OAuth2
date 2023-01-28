import { useRequest } from 'umi';
import { PageContainer, ProCard, Statistic } from '@ant-design/pro-components';
import { Divider } from 'antd';
import { statistics } from '@/services/gemini-oauth2/dashboard/dashboard';

const Dashboard: React.FC = () => {
  const { data } = useRequest(statistics);

  return (
    <PageContainer>
      <ProCard.Group title="核心指标">
        <ProCard>
          <Statistic title="应用数量" value={data?.applicationCount} />
        </ProCard>
        <Divider type="vertical" />
        <ProCard>
          <Statistic title="用户数量" value={data?.userCount} />
        </ProCard>
        <Divider type="vertical" />
        <ProCard>
          <Statistic title="授权个数" value={data?.permissionCount} />
        </ProCard>
      </ProCard.Group>
    </PageContainer>
  );
};
export default Dashboard;
